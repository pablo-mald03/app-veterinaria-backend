package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.request;

import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Principal local get all inventory movement dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllInventoryMovementQueryDto {
    private Long productId;
    private Long batchId;
    private Long purchaseId;
    private Long saleId;
    private MovementType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo;
}