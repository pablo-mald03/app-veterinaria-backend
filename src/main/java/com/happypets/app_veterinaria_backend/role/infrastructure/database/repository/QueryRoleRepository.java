package com.happypets.app_veterinaria_backend.role.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Persistence layer for roles
 *
 */
@Repository
public interface QueryRoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Find by name method
     *
     */
    Optional<RoleEntity> findByAlias(String alias);


    /**
     * Exists by alias method
     *
     */
    boolean existsByAlias(String alias);


    /**
     * Method to find by alias only if it is active
     *
     */
    Set<RoleEntity> findByAliasInAndActiveTrue(Set<String> aliases);
}
