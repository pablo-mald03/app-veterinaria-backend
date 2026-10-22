package com.happypets.app_veterinaria_backend.vaccinationrecord.application.command;

import java.time.LocalDate;

public record RegisterVaccinationRecordCommand(
        Long idCard,
        Long idTreatment,
        Long idDoctor,
        LocalDate applicationDate,
        String notes
) {}