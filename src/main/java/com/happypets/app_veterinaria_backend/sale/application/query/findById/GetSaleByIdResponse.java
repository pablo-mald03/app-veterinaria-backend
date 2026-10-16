package com.happypets.app_veterinaria_backend.sale.application.query.findById;

import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get sale by id response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSaleByIdResponse {
    private Sale sale;
}