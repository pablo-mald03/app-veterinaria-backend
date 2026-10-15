package com.happypets.app_veterinaria_backend.category.application.query.getAll;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all category response
 *
 */
@Data
@AllArgsConstructor
public class GetAllCategoryResponse {
    private PaginationResult<Category> categories;
}