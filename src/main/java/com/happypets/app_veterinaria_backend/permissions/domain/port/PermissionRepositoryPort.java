package com.happypets.app_veterinaria_backend.permissions.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.domain.filter.PermissionFilter;

import java.util.Set;

/**
 * Principal permissions port
 *
 */
public interface PermissionRepositoryPort {

    /**
     * Method to verify if the ids exists
     *
     */
    Set<Long> findExistingIds(Set<Long> requestedIds);

    /**
     * Method to return the permissions by id
     */
    Set<Permission> findByIds(Set<Long> ids);

    /**
     * Method to return the permissions by filters
     */
    PaginationResult<Permission> findAll(PermissionFilter filter, PaginationQuery pagination);

}
