package com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProcessPaymentRequestDTO {
    @NotBlank(message = "El método de pago es obligatorio (ej. CASH, CREDIT_CARD, TRANSFER)")
    private String paymentMethod;
}
