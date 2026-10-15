package com.happypets.app_veterinaria_backend.report.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AttendedPetRecord(
        Long appointmentId,
        Long petId,
        String petName,
        String species,
        LocalDate date,
        String treatment,
        String diagnosis,
        BigDecimal cost
) {}
