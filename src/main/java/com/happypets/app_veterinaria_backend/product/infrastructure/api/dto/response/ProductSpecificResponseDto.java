package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product detail representation
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecificResponseDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String brand;
    private String provider;
}