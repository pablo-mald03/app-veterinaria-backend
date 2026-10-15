package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Get all product stock summary response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductStockSummaryResponseDto {
    private List<ProductStockSummaryResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}