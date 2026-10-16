package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.entity.InventoryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Principal query inventory movement repository
 *
 */
public interface QueryInventoryMovementRepository extends JpaRepository<InventoryMovementEntity, Long>, JpaSpecificationExecutor<InventoryMovementEntity> {

    /**
     * Movement history filtered by product id paginated.
     */
  /*  @Query(value = """
                SELECT new com.happypets.app_veterinaria_backend.inventoryReport.domain.entity.ProductMovementHistoryDetail(
                    m.id,
                    m.type,
                    m.quantity,
                    m.unitCost,
                    m.date,
                    m.origin,
                    m.batch.id,
                    m.sale.id
                )
                FROM InventoryMovementEntity m
                WHERE m.product.id = :productId
                ORDER BY m.date DESC, m.id DESC
            """,
            countQuery = "SELECT COUNT(m) FROM InventoryMovementEntity m WHERE m.product.id = :productId")
    Page<ProductMovementHistoryDetail> findMovementHistoryByProductId(@Param("productId") Long productId, Pageable pageable);*/
}