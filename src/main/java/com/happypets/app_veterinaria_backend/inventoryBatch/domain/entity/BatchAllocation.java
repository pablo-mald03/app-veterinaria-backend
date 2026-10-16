package com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Batch and quantity that will be consumed by the sale
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchAllocation {
    private Long productId;
    private InventoryBatch batch;
    private long quantity;
}