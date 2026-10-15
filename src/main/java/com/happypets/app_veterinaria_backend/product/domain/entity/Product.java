package com.happypets.app_veterinaria_backend.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Principal domain representation entity
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String brand;
    private long minimumStock;
    private BigDecimal price;
    private int profitPercentage;
    private String provider;
    @Builder.Default
    private boolean status = true;
    private Long categoryId;

    private Instant createdAt;
    private Instant updatedAt;
}
