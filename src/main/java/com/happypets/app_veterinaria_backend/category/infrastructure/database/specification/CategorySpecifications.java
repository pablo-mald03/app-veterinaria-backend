package com.happypets.app_veterinaria_backend.category.infrastructure.database.specification;

import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import org.springframework.data.jpa.domain.Specification;


/**
 * Principal filter class specifications for the category persistence layer
 *
 */
public class CategorySpecifications {


    /**
     * Status filter
     *
     */
    public static Specification<CategoryEntity> hasStatus(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }
}