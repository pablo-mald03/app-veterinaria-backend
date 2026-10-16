package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product stock summary response dto
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockSummaryResponseDto {
    private Long productId;
    private String productName;
    private String productSku;
    private long minimumStock;
    private long totalAvailableStock;
    private long activeBatchesCount;
}