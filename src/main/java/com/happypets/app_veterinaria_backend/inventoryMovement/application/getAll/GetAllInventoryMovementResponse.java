package com.happypets.app_veterinaria_backend.inventoryMovement.application.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get all inventory movement response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryMovementResponse {
    private PaginationResult<InventoryMovement> movements;
}