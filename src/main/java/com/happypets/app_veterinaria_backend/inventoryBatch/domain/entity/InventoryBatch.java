package com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseDetailSummary;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal domain representation for inventory batch
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBatch {
    private Long id;
    private BigDecimal unitaryCost;
    private int profitPercentage;
    private long availableQuantity;
    private long originalQuantity;
    private LocalDateTime entryDate;
    private boolean status;

    private Long productId;
    private ProductSummary product;

    private Long purchaseDetailId;
    private PurchaseDetailSummary purchaseDetail;
}