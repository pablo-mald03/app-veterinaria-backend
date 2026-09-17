package com.happypets.app_veterinaria_backend.permissions.infrastructure.database.specification;


import com.happypets.app_veterinaria_backend.permissions.domain.filter.PermissionFilter;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.entity.PermissionEntity;
import org.springframework.data.jpa.domain.Specification;


/**
 * Principal filter class specifications for the permission persistence layer
 *
 */
public class PermissionSpecifications {

    /**
     * Module filter
     *
     */
    public static Specification<PermissionEntity> hasModule(String module) {
        return (root, query, cb) -> (module == null || module.isBlank()) ? null : cb.equal(root.get("module"), module);
    }

    /**
     * Action filter
     *
     */
    public static Specification<PermissionEntity> hasAction(String action) {
        return (root, query, cb) -> (action == null || action.isBlank()) ? null : cb.equal(root.get("action"), action);
    }

    /**
     * Builds the combined specification from a PermissionFilter
     *
     */
    public static Specification<PermissionEntity> byFilter(PermissionFilter filter) {
        return Specification.where(hasModule(filter.getModule()))
                .and(hasAction(filter.getAction()));
    }
}