package com.happypets.app_veterinaria_backend.product.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for product types
 *
 */
@Repository
public interface QueryProductRepository extends JpaRepository<ProductEntity, Long>,
        JpaSpecificationExecutor<ProductEntity> {

    boolean existsBySku(String sku);

    @Modifying
    @Query("update ProductEntity p set p.status = :status where p.id = :id")
    int changeStatus(@Param("id") Long id, @Param("status") boolean status);

    /**
     * Checks if at least one product is assigned to the given category id
     */
    boolean existsByCategoryId(Long categoryId);
}
