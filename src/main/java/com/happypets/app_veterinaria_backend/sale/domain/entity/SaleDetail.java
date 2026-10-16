package com.happypets.app_veterinaria_backend.sale.domain.entity;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import lombok.*;

import java.math.BigDecimal;

/**
 * Principal sale detail domain model
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetail {
    private Long id;
    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private Long productId;
    private ProductSummary product;
}