package com.happypets.app_veterinaria_backend.inventoryBatch.application.getLow;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get low stock product response
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLowStockProductsResponse {
    private PaginationResult<LowStockProductSummary> content;
}