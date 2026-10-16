package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get low stock products response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLowStockProductsResponseDto {
    private List<LowStockProductSummaryResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}