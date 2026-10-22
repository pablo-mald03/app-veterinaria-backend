package com.happypets.app_veterinaria_backend.product.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ExpiringProductSummary;
import com.happypets.app_veterinaria_backend.product.domain.entity.Product;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.domain.filter.ProductFilter;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Principal contest layer for the product repository
 *
 */
public interface ProductRepositoryPort {

    PaginationResult<ProductDetail> findAll(ProductFilter filter, PaginationQuery paginationQuery);

    Optional<ProductDetail> findById(Long id);


    Product save(Product domain);

    Product update(Product domain);

    void changeStatus(Long id, boolean status);

    Product reassignCategory(Long productId, Long categoryId);

    boolean existSku(String sku);

    boolean existsById(Long id);

    boolean existsByCategoryId(Long categoryId);

    PaginationResult<ExpiringProductSummary> findExpiringProducts(LocalDateTime from, LocalDateTime to, PaginationQuery paginationQuery);

}
