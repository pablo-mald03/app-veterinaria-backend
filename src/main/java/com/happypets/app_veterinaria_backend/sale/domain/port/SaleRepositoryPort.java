package com.happypets.app_veterinaria_backend.sale.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.filter.SaleFilter;

import java.util.Optional;

/**
 * Principal sale repository port contest
 *
 */
public interface SaleRepositoryPort {

    Sale save(Sale domain);

    Optional<Sale> findById(Long id);

    Optional<Sale> findByBillNumber(String billNumber);

    boolean existsByBillNumber(String billNumber);

    PaginationResult<Sale> findAll(SaleFilter filter, PaginationQuery paginationQuery);

    PaginationResult<Sale> findAllWithDetails(SaleFilter filter, PaginationQuery paginationQuery);

    long getNextSaleSequence();

}