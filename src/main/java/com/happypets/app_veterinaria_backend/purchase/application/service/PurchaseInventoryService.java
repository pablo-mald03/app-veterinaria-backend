package com.happypets.app_veterinaria_backend.purchase.application.service;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Service helper to regsiter the inventory movement
 *
 */
@Service
@RequiredArgsConstructor
public class PurchaseInventoryService {

    private static final ZoneId GUATEMALA_ZONE = ZoneId.of("America/Guatemala");

    private final InventoryBatchRepositoryPort inventoryBatchRepositoryPort;
    private final InventoryMovementRepositoryPort inventoryMovementRepositoryPort;

    public void registerPurchaseStock(Purchase purchase) {

        LocalDateTime purchaseDate = purchase.getDate() != null
                ? purchase.getDate()
                : LocalDateTime.now(GUATEMALA_ZONE);

        for (PurchaseDetail detail : purchase.getDetails()) {

            Long productId = detail.getProductId() != null
                    ? detail.getProductId()
                    : (detail.getProduct() != null ? detail.getProduct().getId() : null);

            InventoryBatch batch = InventoryBatch.builder()
                    .unitaryCost(detail.getUnitPrice())
                    .profitPercentage(detail.getProfitPercentage())
                    .availableQuantity(detail.getQuantity())
                    .originalQuantity(detail.getQuantity())
                    .entryDate(purchaseDate)
                    .status(true)
                    .productId(productId)
                    .purchaseDetailId(detail.getId())
                    .build();

            InventoryBatch savedBatch = inventoryBatchRepositoryPort.save(batch);

            InventoryMovement movement = InventoryMovement.builder()
                    .type(MovementType.IN)
                    .quantity(detail.getQuantity())
                    .unitCost(detail.getUnitPrice())
                    .origin("COMPRA #" + purchase.getId())
                    .date(purchaseDate)
                    .productId(productId)
                    .batchId(savedBatch.getId())
                    .purchaseId(purchase.getId())
                    .build();

            inventoryMovementRepositoryPort.save(movement);
        }
    }
}