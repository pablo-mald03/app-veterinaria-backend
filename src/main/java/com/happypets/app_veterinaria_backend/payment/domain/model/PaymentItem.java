package com.happypets.app_veterinaria_backend.payment.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentItem {
    private Long id;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String referenceType;
    private Long referenceId;

    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null) {
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}
