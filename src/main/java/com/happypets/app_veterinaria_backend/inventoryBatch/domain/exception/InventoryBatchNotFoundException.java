package com.happypets.app_veterinaria_backend.inventoryBatch.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal exception when the inventory batch was not found
 *
 */
public class InventoryBatchNotFoundException extends ResourceNotFoundException {
    public InventoryBatchNotFoundException(String message) {
        super(message);
    }
}
