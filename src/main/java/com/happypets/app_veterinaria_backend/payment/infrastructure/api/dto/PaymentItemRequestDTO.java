package com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentItemRequestDTO {
    @NotBlank(message = "La descripción del cargo es obligatoria")
    private String description;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal unitPrice;

    @NotBlank(message = "El tipo de referencia es obligatorio (ej. VACCINE, SUPPLY, CONSULTATION)")
    private String referenceType;

    // Puede ser nulo si es un cargo genérico sin ID en base de datos
    private Long referenceId;
}
