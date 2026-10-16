package com.happypets.app_veterinaria_backend.purchase.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.filter.PurchaseFilter;

import java.util.Optional;

/**
 * Principal port for purchase repository
 *
 */
public interface PurchaseRepositoryPort {

    Purchase save(Purchase domain);

    void updateDescription(Long id, String description);

    Optional<Purchase> findById(Long id);

    PaginationResult<Purchase> findAll(PurchaseFilter filter, PaginationQuery paginationQuery);

    PaginationResult<Purchase> findAllWithDetails(PurchaseFilter filter, PaginationQuery paginationQuery);

    boolean existsById(Long id);
}