package com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Batch summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchSummary {
    private Long id;
    private BigDecimal unitaryCost;
    private int profitPercentage;
    private long availableQuantity;
}