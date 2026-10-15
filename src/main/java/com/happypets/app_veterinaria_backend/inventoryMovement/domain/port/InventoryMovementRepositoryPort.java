package com.happypets.app_veterinaria_backend.inventoryMovement.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.filter.InventoryMovementFilter;

import java.util.Optional;

/**
 * Principal port contest for inventory movement
 *
 */
public interface InventoryMovementRepositoryPort {

    InventoryMovement save(InventoryMovement domain);

    Optional<InventoryMovement> findById(Long id);

    PaginationResult<InventoryMovement> findAll(InventoryMovementFilter filter, PaginationQuery paginationQuery);


    //  PaginationResult<ProductMovementHistoryDetail> findMovementHistoryByProductId(Long productId, PaginationQuery paginationQuery);
}