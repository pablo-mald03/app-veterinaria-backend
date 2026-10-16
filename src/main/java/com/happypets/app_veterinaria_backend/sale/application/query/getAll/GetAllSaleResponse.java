package com.happypets.app_veterinaria_backend.sale.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal get all sale response application layer
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllSaleResponse {
    private PaginationResult<Sale> sales;
}