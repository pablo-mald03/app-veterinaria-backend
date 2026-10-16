package com.happypets.app_veterinaria_backend.sale.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Principal create sale detail request
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSaleItemRequest {
    private Long productId;
    private long quantity;
    private BigDecimal unitPrice;
}