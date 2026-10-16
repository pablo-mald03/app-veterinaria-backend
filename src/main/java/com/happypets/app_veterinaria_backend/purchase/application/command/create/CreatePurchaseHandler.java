package com.happypets.app_veterinaria_backend.purchase.application.command.create;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductNotFountException;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import com.happypets.app_veterinaria_backend.purchase.application.service.PurchaseInventoryService;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseDetail;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import com.happypets.app_veterinaria_backend.purchase.domain.exception.PurchaseNotFoundException;
import com.happypets.app_veterinaria_backend.purchase.domain.port.PurchaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Principal create purchase handler
 *
 */
@Service
@RequiredArgsConstructor
public class CreatePurchaseHandler implements RequestHandler<CreatePurchaseRequest, CreatePurchaseResponse> {

    // Ports
    private final PurchaseRepositoryPort purchaseRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final PurchaseInventoryService purchaseInventoryService;
    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatePurchaseResponse handle(CreatePurchaseRequest request) {

        BigDecimal ivaPercent = request.getIva() != null ? request.getIva() : new BigDecimal("12");
        BigDecimal ivaRate = ivaPercent.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        BigDecimal calculatedSubtotal = BigDecimal.ZERO;
        BigDecimal calculatedTotalIva = BigDecimal.ZERO;
        List<PurchaseDetail> domainDetails = new ArrayList<>();

        for (CreatePurchaseDetailItemRequest detailItem : request.getDetails()) {

            if (!productRepositoryPort.existsById(detailItem.getProductId())) {
                throw new ProductNotFountException("El producto con ID " + detailItem.getProductId() + " no existe.");
            }

            BigDecimal itemSubtotal = detailItem.getUnitPrice().multiply(BigDecimal.valueOf(detailItem.getQuantity()));
            BigDecimal itemIva = itemSubtotal.multiply(ivaRate).setScale(2, RoundingMode.HALF_UP);

            calculatedSubtotal = calculatedSubtotal.add(itemSubtotal);
            calculatedTotalIva = calculatedTotalIva.add(itemIva);

            PurchaseDetail detail = PurchaseDetail.builder()
                    .quantity(detailItem.getQuantity())
                    .unitPrice(detailItem.getUnitPrice())
                    .iva(itemIva)
                    .profitPercentage(detailItem.getProfitPercentage())
                    .productId(detailItem.getProductId())
                    .build();

            domainDetails.add(detail);
        }

        BigDecimal calculatedTotal = calculatedSubtotal.add(calculatedTotalIva);
        Long userId = authenticatedUserPort.getUserId();


        Purchase purchase = Purchase.builder()
                .description(request.getDescription())
                .subtotal(calculatedSubtotal)
                .total(calculatedTotal)
                .status(PurchaseStatus.COMPLETED)
                .details(domainDetails)
                .userId(userId)
                .build();

        Purchase savedPurchase = purchaseRepositoryPort.save(purchase);

        purchaseInventoryService.registerPurchaseStock(savedPurchase);

        Purchase fullPurchase = purchaseRepositoryPort.findById(savedPurchase.getId())
                .orElseThrow(() -> new PurchaseNotFoundException("Error al recuperar la compra registrada con ID: " + savedPurchase.getId()));

        return new CreatePurchaseResponse(fullPurchase);
    }

    @Override
    public Class<CreatePurchaseRequest> getRequestType() {
        return CreatePurchaseRequest.class;
    }
}