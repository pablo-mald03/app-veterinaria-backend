package com.happypets.app_veterinaria_backend.role.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    Optional<RoleEntity> findByName(String name);
}
