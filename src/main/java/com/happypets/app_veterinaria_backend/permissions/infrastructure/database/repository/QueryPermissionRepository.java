package com.happypets.app_veterinaria_backend.permissions.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Prncipal permission repository
 *
 */
@Repository
public interface QueryPermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Page<PermissionEntity> findAll(Specification<PermissionEntity> specification, Pageable pageable);
}
