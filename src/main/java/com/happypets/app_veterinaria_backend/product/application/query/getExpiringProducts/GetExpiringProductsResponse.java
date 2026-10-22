package com.happypets.app_veterinaria_backend.product.application.query.getExpiringProducts;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ExpiringProductSummary;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get expiring products response
 *
 */
@Data
@AllArgsConstructor
public class GetExpiringProductsResponse {
    private PaginationResult<ExpiringProductSummary> content;
}