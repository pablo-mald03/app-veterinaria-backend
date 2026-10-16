package com.happypets.app_veterinaria_backend.sale.application.service;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleItemRequest;
import com.happypets.app_veterinaria_backend.sale.application.command.CreateSaleRequest;
import com.happypets.app_veterinaria_backend.sale.application.events.SaleCreatedEvent;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleDetail;
import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import com.happypets.app_veterinaria_backend.sale.domain.exception.DuplicateBillNumberException;
import com.happypets.app_veterinaria_backend.sale.domain.exception.InsufficientStockException;
import com.happypets.app_veterinaria_backend.sale.domain.port.SaleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Principal inventory service
 *
 */
@Service
@RequiredArgsConstructor
public class SaleInventoryService {
    private final SaleRepositoryPort saleRepositoryPort;
    private final InventoryBatchRepositoryPort inventoryBatchRepositoryPort;
    private final InventoryMovementRepositoryPort inventoryMovementRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    /**
     * Publisher pdf generator
     *
     */
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Transactional method to execute complete sale and inventory deduction (UEPS)
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public Sale processSale(CreateSaleRequest request) {

        String finalBillNumber = resolveBillNumber(request.getBillNumber());

        validateRequestItems(request.getItems());

        Map<Long, Long> requiredQuantitiesByProduct = groupQuantitiesByProduct(request.getItems());
        validateAvailableStock(requiredQuantitiesByProduct);
        validateUnitPrices(request.getItems());

        Sale saleToSave = buildSaleDomain(request, finalBillNumber);
        Sale savedSale = saleRepositoryPort.save(saleToSave);

        deductInventoryAndRegisterMovements(savedSale, requiredQuantitiesByProduct);

        eventPublisher.publishEvent(new SaleCreatedEvent(savedSale.getId()));

        return savedSale;
    }

    /**
     * Validation of unit prices: each item cannot be sold below the cost of the batches it consumes.
     *
     */
    private void validateUnitPrices(List<CreateSaleItemRequest> items) {
        Map<Long, List<InventoryBatch>> batchesByProduct = new HashMap<>();
        Map<Long, Integer> currentBatchIndex = new HashMap<>();
        Map<Long, Long> consumedInCurrentBatch = new HashMap<>();

        for (CreateSaleItemRequest item : items) {
            Long productId = item.getProductId();

            List<InventoryBatch> batches = batchesByProduct.computeIfAbsent(
                    productId, inventoryBatchRepositoryPort::findAvailableBatchesByProductIdUEPSWithLock);

            int index = currentBatchIndex.getOrDefault(productId, 0);
            long consumed = consumedInCurrentBatch.getOrDefault(productId, 0L);
            long remaining = item.getQuantity();
            BigDecimal highestCost = BigDecimal.ZERO;

            while (remaining > 0 && index < batches.size()) {
                InventoryBatch batch = batches.get(index);
                long availableInBatch = batch.getAvailableQuantity() - consumed;
                long taken = Math.min(availableInBatch, remaining);

                highestCost = highestCost.max(batch.getUnitaryCost());
                remaining -= taken;
                consumed += taken;

                if (consumed >= batch.getAvailableQuantity()) {
                    index++;
                    consumed = 0;
                }
            }

            currentBatchIndex.put(productId, index);
            consumedInCurrentBatch.put(productId, consumed);

            if (item.getUnitPrice().compareTo(highestCost) < 0) {
                throw new IllegalArgumentException(
                        "El precio unitario del producto ID " + item.getProductId() + " (Q" + item.getUnitPrice() +
                                ") no puede ser menor al costo de compra (Q" + highestCost + ").");
            }
        }
    }

    /**
     * Validation of bill number
     */
    private String resolveBillNumber(String rawBillNumber) {
        String sanitized = sanitizeBillNumber(rawBillNumber);

        if (sanitized != null) {
            if (saleRepositoryPort.existsByBillNumber(sanitized)) {
                throw new DuplicateBillNumberException("El número de factura '" + sanitized + "' ya fue registrado.");
            }
            return sanitized;
        }

        return generateNextBillNumber();
    }

    /**
     * Autogenerate correlative
     */
    private String generateNextBillNumber() {
        long nextSequence = saleRepositoryPort.getNextSaleSequence();
        return String.format("FAC-%d-%06d", LocalDate.now().getYear(), nextSequence);
    }

    /**
     * Validation of date request
     */
    private void validateRequestItems(List<CreateSaleItemRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un producto.");
        }
    }

    /**
     * Validation of quantities
     *
     */
    private Map<Long, Long> groupQuantitiesByProduct(List<CreateSaleItemRequest> items) {
        return items.stream()
                .collect(Collectors.groupingBy(
                        CreateSaleItemRequest::getProductId,
                        Collectors.summingLong(CreateSaleItemRequest::getQuantity)
                ));
    }

    /**
     * Validation of available stock
     *
     */
    private void validateAvailableStock(Map<Long, Long> requiredQuantitiesByProduct) {
        for (Map.Entry<Long, Long> entry : requiredQuantitiesByProduct.entrySet()) {
            Long productId = entry.getKey();
            Long requiredQty = entry.getValue();
            long availableStock = inventoryBatchRepositoryPort.getTotalAvailableStock(productId);

            if (availableStock < requiredQty) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto ID " + productId +
                                ". Requerido: " + requiredQty + ", Disponible: " + availableStock
                );
            }
        }
    }

    /**
     * Builder to sale domain
     */
    private Sale buildSaleDomain(CreateSaleRequest request, String billNumber) {
        List<SaleDetail> saleDetails = new ArrayList<>();
        BigDecimal saleSubtotal = BigDecimal.ZERO;

        for (CreateSaleItemRequest item : request.getItems()) {
            BigDecimal detailSubtotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            saleSubtotal = saleSubtotal.add(detailSubtotal);

            SaleDetail detail = SaleDetail.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .subtotal(detailSubtotal)
                    .build();

            saleDetails.add(detail);
        }

        BigDecimal ivaPercent = request.getIva() != null ? request.getIva() : new BigDecimal("12");
        BigDecimal ivaRate = ivaPercent.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        BigDecimal saleIva = saleSubtotal.multiply(ivaRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal saleTotal = saleSubtotal.add(saleIva).setScale(2, RoundingMode.HALF_UP);

        LocalDateTime saleDate = request.getDate() != null
                ? request.getDate()
                : LocalDateTime.now(ZoneId.of("America/Guatemala"));

        Long userId = authenticatedUserPort.getUserId();

        return Sale.builder()
                .billNumber(billNumber)
                .date(saleDate)
                .subtotal(saleSubtotal)
                .iva(saleIva)
                .total(saleTotal)
                .userId(userId)
                .status(SaleStatus.COMPLETED)
                .details(saleDetails)
                .build();
    }

    /**
     * Principal method to verify the stock and register inventory movements
     *
     */
    private void deductInventoryAndRegisterMovements(Sale savedSale, Map<Long, Long> requiredQuantitiesByProduct) {
        List<InventoryMovement> movementsToSave = new ArrayList<>();
        LocalDateTime now = savedSale.getDate();

        for (Map.Entry<Long, Long> entry : requiredQuantitiesByProduct.entrySet()) {
            Long productId = entry.getKey();
            long remainingToDeduct = entry.getValue();

            List<InventoryBatch> batches = inventoryBatchRepositoryPort.findAvailableBatchesByProductIdUEPSWithLock(productId);

            for (InventoryBatch batch : batches) {
                if (remainingToDeduct <= 0) {
                    break;
                }

                long currentAvailable = batch.getAvailableQuantity();
                long deductedQuantity = Math.min(currentAvailable, remainingToDeduct);
                long newAvailable = currentAvailable - deductedQuantity;
                boolean isBatchStillActive = newAvailable > 0;

                inventoryBatchRepositoryPort.updateAvailableQuantity(batch.getId(), newAvailable, isBatchStillActive);

                InventoryMovement movement = InventoryMovement.builder()
                        .type(MovementType.OUT)
                        .quantity(deductedQuantity)
                        .unitCost(batch.getUnitaryCost())
                        .date(now)
                        .origin("VENTA #" + (savedSale.getBillNumber() != null ? savedSale.getBillNumber() : savedSale.getId()))
                        .productId(productId)
                        .batchId(batch.getId())
                        .saleId(savedSale.getId())
                        .build();

                movementsToSave.add(movement);

                remainingToDeduct -= deductedQuantity;
            }

            if (remainingToDeduct > 0) {
                throw new InsufficientStockException("Inconsistencia en inventario. Faltó descontar " + remainingToDeduct + " unidades para el producto ID " + productId);
            }
        }

        for (InventoryMovement movement : movementsToSave) {
            inventoryMovementRepositoryPort.save(movement);
        }
    }


    /**
     * Sanitizer helper
     *
     */
    private String sanitizeBillNumber(String billNumber) {
        if (billNumber == null || billNumber.isBlank()) {
            return null;
        }

        return billNumber
                .trim()
                .replaceAll("^#+", "")
                .replaceAll("\\s+", "")
                .replaceAll("[^a-zA-Z0-9\\-]", "")
                .toUpperCase();
    }
}