package com.happypets.app_veterinaria_backend.purchase.application.query.finById;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get purchase by id response
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPurchaseByIdResponse {
    private Purchase purchase;
}