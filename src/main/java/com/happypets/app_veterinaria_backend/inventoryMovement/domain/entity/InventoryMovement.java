package com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.BatchSummary;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal domain representation for inventory movement
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovement {
    private Long id;
    private MovementType type;
    private long quantity;
    private BigDecimal unitCost;
    private LocalDateTime date;
    private String origin;

    private Long productId;
    private ProductSummary product;

    private Long batchId;
    private BatchSummary batch;

    private Long purchaseId;
    private PurchaseSummary purchase;

//    private Long saleId;
//    private SaleSummary sale;
}