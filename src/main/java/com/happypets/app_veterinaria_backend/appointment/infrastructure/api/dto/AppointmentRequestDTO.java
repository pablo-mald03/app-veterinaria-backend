package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {
    @NotNull(message = "Debe especificar a qué mascota atenderá (pet_id)")
    private Long petId;

    @NotNull(message = "Debe asignar un veterinario a la cita (user_id)")
    private Long userId;

    private Long roomId;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @FutureOrPresent(message = "No puede agendar citas en el pasado")
    private LocalDate date;

    @NotNull(message = "La hora de la cita es obligatoria")
    private LocalTime hour;

    @NotBlank(message = "El motivo de la consulta (descripción) es obligatorio")
    private String description;

    private String diagnosis;
}
