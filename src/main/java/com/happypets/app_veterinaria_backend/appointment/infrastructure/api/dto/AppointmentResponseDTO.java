package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private Long petId;
    private Long userId;
    private Long roomId;
    private LocalDate date;
    private LocalTime hour;
    private String description;
    private String diagnosis;
    private String treatment;
    private BigDecimal cost;
    private String status;
}
