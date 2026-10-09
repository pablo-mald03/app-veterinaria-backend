package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentDiagnosisRequestDTO {
    @NotBlank(message = "El diagnostico esta vacio")
    private String diagnosis;
}
