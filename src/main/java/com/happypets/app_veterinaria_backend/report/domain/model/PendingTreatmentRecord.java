package com.happypets.app_veterinaria_backend.report.domain.model;

import java.time.LocalDate;

public record PendingTreatmentRecord(
        Long idPet,
        String petName,
        String species,
        String treatmentName,
        Integer applicationNumber,
        LocalDate nextDoseDate
) {}