package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentStatusRequestDTO {
    @NotBlank(message = "El estado es obligatorio")
    private String status;

}
