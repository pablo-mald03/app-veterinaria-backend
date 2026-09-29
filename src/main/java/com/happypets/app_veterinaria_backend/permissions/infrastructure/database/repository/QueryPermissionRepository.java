package com.happypets.app_veterinaria_backend.permissions.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Prncipal permission repository
 *
 */
@Repository
public interface QueryPermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Page<PermissionEntity> findAll(Specification<PermissionEntity> specification, Pageable pageable);

    /**
     * Query to get the distinct modules ordered alphabetically
     *
     */
    @Query("SELECT DISTINCT p.module FROM PermissionEntity p WHERE p.module IS NOT NULL ORDER BY p.module")
    List<String> findDistinctModules();

    /**
     * Query to get the distinct actions ordered alphabetically
     *
     */
    @Query("SELECT DISTINCT p.action FROM PermissionEntity p WHERE p.action IS NOT NULL ORDER BY p.action")
    List<String> findDistinctActions();
}
