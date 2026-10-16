package com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentItemDTO {
    private Long id;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String referenceType;
    private Long referenceId;
}
