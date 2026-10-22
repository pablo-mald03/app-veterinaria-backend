package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FollowUpRequestDTO {
    @NotNull(message = "La fecha de seguimiento es obligatoria")
    private LocalDate date;

    @NotNull(message = "La hora de seguimiento es obligatoria")
    private LocalTime hour;

    private String description;
}
