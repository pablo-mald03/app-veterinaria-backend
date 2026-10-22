package com.happypets.app_veterinaria_backend.product.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.product.domain.entity.ExpiringProductSummary;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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


    @Query(value = """
            SELECT new com.happypets.app_veterinaria_backend.product.domain.entity.ExpiringProductSummary(
                p.id, p.name, p.sku, p.description, p.expirationDate
            )
            FROM ProductEntity p
            WHERE p.status = true
              AND p.expirationDate BETWEEN :from AND :to
            """, countQuery = """
            SELECT COUNT(p)
            FROM ProductEntity p
            WHERE p.status = true
              AND p.expirationDate BETWEEN :from AND :to
            """)
    Page<ExpiringProductSummary> findExpiringProducts(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    
    /**
     * Checks if at least one product is assigned to the given category id
     */
    boolean existsByCategoryId(Long categoryId);
}
