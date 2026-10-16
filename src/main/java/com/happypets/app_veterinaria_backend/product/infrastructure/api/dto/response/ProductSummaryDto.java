package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal product summary dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDto {
    private Long id;
    private String name;
    private String sku;
    private long minimumStock;

}