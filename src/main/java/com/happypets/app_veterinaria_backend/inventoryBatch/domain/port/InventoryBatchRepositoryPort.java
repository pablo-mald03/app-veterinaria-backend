package com.happypets.app_veterinaria_backend.inventoryBatch.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.filter.InventoryBatchFilter;

import java.util.List;
import java.util.Optional;

/**
 * Principal port contest for inventory batch
 *
 */
public interface InventoryBatchRepositoryPort {

    InventoryBatch save(InventoryBatch domain);

    Optional<InventoryBatch> findById(Long id);

    List<InventoryBatch> findAvailableBatchesByProductIdUEPS(Long productId);

    List<InventoryBatch> findAvailableBatchesByProductIdUEPSWithLock(Long productId);

    long getTotalAvailableStock(Long productId);

    void updateAvailableQuantity(Long batchId, long newAvailableQuantity, boolean status);

    PaginationResult<InventoryBatch> findAll(InventoryBatchFilter filter, PaginationQuery paginationQuery);

    PaginationResult<LowStockProductSummary> findLowStockProducts(PaginationQuery paginationQuery);

    PaginationResult<ProductStockSummary> findAllSummary(PaginationQuery paginationQuery);

}