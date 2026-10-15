package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category response dto
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean status;
}