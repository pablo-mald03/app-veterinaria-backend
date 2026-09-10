package com.happypets.app_veterinaria_backend.user.domain.port;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;

import java.util.Optional;

/**
 * Principal role port
 *
 */
public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);

    PaginationResult<Role> findAll(PaginationQuery paginationQuery);

}
