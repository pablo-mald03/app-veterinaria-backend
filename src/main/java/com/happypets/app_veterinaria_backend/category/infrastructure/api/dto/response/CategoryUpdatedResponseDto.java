package com.happypets.app_veterinaria_backend.category.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal category updated response
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdatedResponseDto {
    private String name;
}
