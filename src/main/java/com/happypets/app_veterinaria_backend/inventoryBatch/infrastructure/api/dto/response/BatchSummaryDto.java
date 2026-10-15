package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Batch summary dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchSummaryDto {
    private Long id;
    private BigDecimal unitaryCost;
    private int profitPercentage;
    private long availableQuantity;
}