package com.happypets.app_veterinaria_backend.vaccinationcard.domain.model;

import java.time.LocalDate;
import java.util.List;

public record VaccinationCardReport(
        Long idCard,
        LocalDate creationDate,
        Long idPet,
        String petName,
        String species,
        String breed,
        int age,
        double weight,
        List<Entry> entries
) {
    public record Entry(
            String vaccineName,
            Integer doseNumber,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String batchNumber,
            String notes
    ) {}
}
