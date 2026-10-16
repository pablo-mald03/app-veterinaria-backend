package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto projection for low stock product alerts
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LowStockProductSummaryResponseDto {
    private Long productId;
    private String name;
    private String sku;
    private String description;
    private long currentStock;
    private long minimumStock;
}