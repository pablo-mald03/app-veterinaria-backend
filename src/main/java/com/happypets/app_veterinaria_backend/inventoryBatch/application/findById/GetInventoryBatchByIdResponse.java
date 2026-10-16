package com.happypets.app_veterinaria_backend.inventoryBatch.application.findById;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get inventory batch by id response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInventoryBatchByIdResponse {
    private InventoryBatch batch;
}