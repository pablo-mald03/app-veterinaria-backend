package com.happypets.app_veterinaria_backend.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummary {
    private Long id;
    private String name;
    private String sku;
    private long minimumStock;
}