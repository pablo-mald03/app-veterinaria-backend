package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Principal get all inventory query dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllInventoryBatchQueryDto {
    private Long productId;
    private Boolean status;
    private Boolean onlyAvailable;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo;
}