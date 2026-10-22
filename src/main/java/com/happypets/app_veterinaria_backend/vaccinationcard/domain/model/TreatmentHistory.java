package com.happypets.app_veterinaria_backend.vaccinationcard.domain.model;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * Qué tratamientos tiene la mascota, cuándo fue la última vez y cuándo toca de nuevo.
 */
public record TreatmentHistory(Long idCard, Long idPet, List<TreatmentLine> treatments) {

    public record TreatmentLine(
            Long idTreatment,
            String name,
            int applications,
            LocalDate lastApplicationDate,
            LocalDate nextDueDate,
            boolean overdue,
            List<VaccinationRecord> history
    ) {}
}