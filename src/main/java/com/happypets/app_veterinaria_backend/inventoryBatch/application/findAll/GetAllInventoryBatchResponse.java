package com.happypets.app_veterinaria_backend.inventoryBatch.application.findAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get all inventory batch response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryBatchResponse {
    private PaginationResult<InventoryBatch> batches;
}