package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response;

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
public class GetAllCategoryResponseDto {
    private List<CategoryResponseDto> categories;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}