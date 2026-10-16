package com.happypets.app_veterinaria_backend.inventoryMovement.application.getById;

import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get inventory movement response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInventoryMovementByIdResponse {
    private InventoryMovement movement;
}