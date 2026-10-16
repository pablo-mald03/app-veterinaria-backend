package com.happypets.app_veterinaria_backend.product.domain.filter;

import lombok.Builder;
import lombok.Data;

/**
 * Principal product filter domain representation
 *
 */
@Data
@Builder
public class ProductFilter {
    private Long priceFrom;
    private Long priceTo;
    private Long categoryId;
    private String sku;
    private String provider;
}