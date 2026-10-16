package com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long appointmentId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private List<PaymentItemDTO> items;
}
