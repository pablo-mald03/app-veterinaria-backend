package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal Get All query category dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductQueryDto {
    private Long priceFrom;
    private Long priceTo;
    private Long categoryId;
    private String sku;
    private String provider;
}