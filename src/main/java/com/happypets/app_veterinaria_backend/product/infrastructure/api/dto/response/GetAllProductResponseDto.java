package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Category pagination response class dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductResponseDto {
    private List<ProductDetailResponseDto> products;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}