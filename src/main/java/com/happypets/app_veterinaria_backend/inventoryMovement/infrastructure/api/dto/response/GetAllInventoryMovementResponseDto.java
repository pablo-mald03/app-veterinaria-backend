package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get all inventory movement response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInventoryMovementResponseDto {
    private List<InventoryMovementResponseDto> movements;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}