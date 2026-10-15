package com.happypets.app_veterinaria_backend.product.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all category response
 *
 */
@Data
@AllArgsConstructor
public class GetAllProductResponse {
    private PaginationResult<ProductDetail> products;
}