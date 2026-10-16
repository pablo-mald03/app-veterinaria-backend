package com.happypets.app_veterinaria_backend.sale.application.command;

import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create sale response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSaleResponse {
    private Sale sale;
}