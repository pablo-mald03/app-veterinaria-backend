package com.happypets.app_veterinaria_backend.inventoryBatch.application.getSummary;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all product stock summary response class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductStockSummaryResponse {
    private PaginationResult<ProductStockSummary> summaries;
}