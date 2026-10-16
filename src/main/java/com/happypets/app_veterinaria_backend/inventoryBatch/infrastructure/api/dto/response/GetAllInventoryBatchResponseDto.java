package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get all inventory batches response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryBatchResponseDto {
    private List<InventoryBatchResponseDto> batches;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}