package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response.SimpleCategoryResponseDto;
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
public class ProductDetailResponseDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String brand;
    private int profitPercentage;
    private long minimumStock;
    private long price;
    private String provider;
    private boolean status;

    private SimpleCategoryResponseDto category;

}