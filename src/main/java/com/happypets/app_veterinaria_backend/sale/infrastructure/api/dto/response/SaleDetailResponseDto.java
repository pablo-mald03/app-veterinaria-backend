package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal sale detail response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailResponseDto {
    private Long id;
    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private ProductSummaryDto product;
}