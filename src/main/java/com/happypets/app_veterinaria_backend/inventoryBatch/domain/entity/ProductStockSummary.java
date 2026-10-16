package com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockSummary {
    private Long productId;
    private String productName;
    private String productSku;
    private long minimumStock;
    private long totalAvailableStock;
    private long activeBatchesCount;
}