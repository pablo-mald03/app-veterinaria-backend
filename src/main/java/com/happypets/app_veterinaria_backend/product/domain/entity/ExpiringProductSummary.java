package com.happypets.app_veterinaria_backend.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Expiring product summary domain model
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpiringProductSummary {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private LocalDateTime expirationDate;
}