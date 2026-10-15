package com.happypets.app_veterinaria_backend.category.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for category types
 *
 */
@Repository
public interface QueryCategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {

    @Modifying
    @Query("update CategoryEntity c set c.status = :status where c.id = :id")
    int changeStatus(@Param("id") Long id, @Param("status") boolean status);

    boolean existsByAlias(String alias);
}
