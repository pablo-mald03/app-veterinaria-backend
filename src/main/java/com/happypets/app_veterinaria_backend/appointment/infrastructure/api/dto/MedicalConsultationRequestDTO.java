package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicalConsultationRequestDTO {

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnosis;

    @NotBlank(message = "El tratamiento es obligatorio")
    private String treatment;

    @NotNull(message = "El costo no puede ser nulo")
    @PositiveOrZero(message = "El costo no puede ser negativo")
    private BigDecimal cost;
}
