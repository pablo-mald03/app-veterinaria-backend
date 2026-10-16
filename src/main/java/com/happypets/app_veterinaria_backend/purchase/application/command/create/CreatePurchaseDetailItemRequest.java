package com.happypets.app_veterinaria_backend.purchase.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseDetailItemRequest {
    private Long productId;
    private long quantity;
    private BigDecimal unitPrice;
    private int profitPercentage;
}