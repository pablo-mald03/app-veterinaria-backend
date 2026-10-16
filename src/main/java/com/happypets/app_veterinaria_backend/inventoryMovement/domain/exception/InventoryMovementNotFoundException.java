package com.happypets.app_veterinaria_backend.inventoryMovement.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal exception when the inventory movement was not found
 *
 */
public class InventoryMovementNotFoundException extends ResourceNotFoundException {
    public InventoryMovementNotFoundException(String message) {
        super(message);
    }
}
