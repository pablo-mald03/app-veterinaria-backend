package com.happypets.app_veterinaria_backend.purchase.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get all purchase response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPurchaseResponse {
    private PaginationResult<Purchase> purchases;
}