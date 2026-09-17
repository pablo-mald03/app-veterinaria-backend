package com.happypets.app_veterinaria_backend.role.domain.port;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;

import java.util.Optional;
import java.util.Set;

/**
 * Principal role port
 *
 */
public interface RoleRepositoryPort {
    Optional<Role> findByAlias(String alias);

    PaginationResult<Role> findAll(PaginationQuery paginationQuery);

    Role save(Role role);

    Role patch(Role role);

    Role update(Role role);

    boolean existsByAlias(String alias);

    Set<Role> findByAliases(Set<String> aliases);

    Optional<Role> findById(Long id);
}
