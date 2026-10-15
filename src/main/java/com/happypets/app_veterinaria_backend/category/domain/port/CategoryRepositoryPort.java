package com.happypets.app_veterinaria_backend.category.domain.port;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.filter.CategoryFilter;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;

import java.util.Optional;

/**
 * Principal contest layer for the log repository
 *
 */
public interface CategoryRepositoryPort {
    PaginationResult<Category> findAll(CategoryFilter filter, PaginationQuery paginationQuery);

    Optional<Category> findById(Long id);

    Category save(Category domain);

    Category update(Category domain);

    void changeStatus(Long id, boolean status);

    boolean existsByAlias(String alias);
}