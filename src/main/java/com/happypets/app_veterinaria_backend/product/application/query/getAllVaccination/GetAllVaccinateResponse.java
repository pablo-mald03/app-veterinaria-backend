package com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination;

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
public class GetAllVaccinateResponse {
    private PaginationResult<ProductDetail> vaccinations;
}