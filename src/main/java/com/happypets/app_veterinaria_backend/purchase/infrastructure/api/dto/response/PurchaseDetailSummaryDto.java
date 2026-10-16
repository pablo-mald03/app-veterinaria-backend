package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal purchase detail summary dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailSummaryDto {
    private Long id;
    private Long purchaseId;
    private BigDecimal unitPrice;
    private int profitPercentage;
}