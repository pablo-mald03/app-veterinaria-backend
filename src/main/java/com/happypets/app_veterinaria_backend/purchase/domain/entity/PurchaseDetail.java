package com.happypets.app_veterinaria_backend.purchase.domain.entity;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import lombok.*;

import java.math.BigDecimal;

/**
 * Principal purchase detail domain entity
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetail {
    private Long id;
    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal iva;
    private int profitPercentage;
    private Long productId;
    private ProductSummary product;
}