package com.happypets.app_veterinaria_backend.report.domain.model;

import java.time.LocalDate;

public record PendingVaccineRecord(
        Long petId,
        String petName,
        String species,
        String vaccineName,
        Integer lastDoseNumber,
        LocalDate nextDoseDate
) {}
