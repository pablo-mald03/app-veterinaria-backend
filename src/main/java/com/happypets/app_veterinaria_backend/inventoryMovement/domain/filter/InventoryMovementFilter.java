package com.happypets.app_veterinaria_backend.inventoryMovement.domain.filter;

import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal inventory movement filter
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovementFilter {
    private Long productId;
    private Long batchId;
    private Long purchaseId;
    private Long saleId;
    private MovementType type;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}