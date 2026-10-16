package com.happypets.app_veterinaria_backend.purchase.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal purchase entity detail summary
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailSummary {
    private Long id;
    private Long purchaseId;
    private BigDecimal unitPrice;
    private int profitPercentage;
}