package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Principal query inventory batch repository
 *
 */
@Repository
public interface QueryInventoryBatchRepository extends JpaRepository<InventoryBatchEntity, Long>, JpaSpecificationExecutor<InventoryBatchEntity> {

    /**
     * Query to get consolidated stock grouped by product
     *
     */
    @Query(value = """
                SELECT new com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary(
                    p.id,
                    p.name,
                    p.sku,
                    p.minimumStock,
                    COALESCE(SUM(b.availableQuantity), 0L),
                    COUNT(b.id)
                )
                FROM ProductEntity p
                LEFT JOIN InventoryBatchEntity b ON b.product.id = p.id AND b.status = true
                WHERE p.status = true
                GROUP BY p.id, p.name, p.sku, p.minimumStock
            """, countQuery = "SELECT COUNT(p) FROM ProductEntity p WHERE p.status = true")
    Page<ProductStockSummary> findProductStockSummaries(Pageable pageable);

    /**
     * Finds products paginated where total active batch stock is less than or equal to product minimum stock
     *
     */
    @Query(value = """
                SELECT new com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary(
                    p.id,
                    p.name,
                    p.sku,
                    p.description,
                    COALESCE(SUM(b.availableQuantity), 0L),
                    p.minimumStock
                )
                FROM ProductEntity p
                LEFT JOIN InventoryBatchEntity b ON b.product.id = p.id AND b.status = true
                WHERE p.status = true
                GROUP BY p.id, p.name, p.sku, p.description, p.minimumStock
                HAVING COALESCE(SUM(b.availableQuantity), 0L) <= p.minimumStock
            """, countQuery = """
                SELECT COUNT(p)
                FROM ProductEntity p
                WHERE p.status = true AND (
                    SELECT COALESCE(SUM(b.availableQuantity), 0L)
                    FROM InventoryBatchEntity b
                    WHERE b.product.id = p.id AND b.status = true
                ) <= p.minimumStock
            """)
    Page<LowStockProductSummary> findLowStockProducts(Pageable pageable);

    /**
     * fetch all data to validate the stock by product id
     */
    @Query("SELECT COALESCE(SUM(b.availableQuantity), 0) FROM InventoryBatchEntity b WHERE b.product.id = :productId AND b.status = true AND b.availableQuantity > 0")
    long getTotalAvailableStockByProductId(@Param("productId") Long productId);

    /**
     * Block the transaction at the final with other transactions
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM InventoryBatchEntity b WHERE b.product.id = :productId AND b.status = true AND b.availableQuantity > 0 ORDER BY b.entryDate DESC, b.id DESC")
    List<InventoryBatchEntity> findAvailableBatchesWithLock(@Param("productId") Long productId);


}