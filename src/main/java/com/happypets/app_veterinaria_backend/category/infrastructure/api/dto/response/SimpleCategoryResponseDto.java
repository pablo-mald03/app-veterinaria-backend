package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple category response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCategoryResponseDto {
    private Long id;
    private String name;
    private boolean status;
}