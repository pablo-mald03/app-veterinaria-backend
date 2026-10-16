package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal create purchase detail response
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailResponseDto {
    private Long id;
    private long quantity;
    private BigDecimal unitPrice;
    private int profitPercentage;
    private BigDecimal iva;
    private ProductSummaryDto product;
}