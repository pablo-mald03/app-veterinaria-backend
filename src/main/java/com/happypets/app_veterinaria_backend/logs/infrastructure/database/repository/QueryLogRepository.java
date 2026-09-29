package com.happypets.app_veterinaria_backend.logs.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.logs.infrastructure.database.entity.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Persistence layer for logs
 *
 */
@Repository
public interface QueryLogRepository extends JpaRepository<LogEntity, Long> {

    @Query("SELECT DISTINCT log.module FROM LogEntity log ORDER BY log.module")
    List<String> findDistinctModules();

    Page<LogEntity> findAll(Specification<LogEntity> specification, Pageable pageable);
}
