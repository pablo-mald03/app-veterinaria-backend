package com.happypets.app_veterinaria_backend.payment.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Payment {
    private Long id;
    private Long appointmentId;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private String paymentMethod;
    private String status; // PENDING, PAID, CANCELLED
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private List<PaymentItem> items = new ArrayList<>();

    public void addItem(PaymentItem item) {
        item.calculateSubtotal();
        this.items.add(item);
        this.totalAmount = this.totalAmount.add(item.getSubtotal());
    }
}
