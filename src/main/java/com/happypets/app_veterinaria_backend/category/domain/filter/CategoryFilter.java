package com.happypets.app_veterinaria_backend.category.domain.filter;

import lombok.Builder;
import lombok.Data;

/**
 * Product filter properties
 *
 */
@Data
@Builder
public class CategoryFilter {
    private Boolean status;
}