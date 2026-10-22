package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto;

import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordCommand;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VaccinationRecordRequestDTO(
        @NotNull Long idCard,
        @NotNull Long idTreatment,
        @NotNull Long idDoctor,
        @NotNull LocalDate applicationDate,
        String notes
) {
    public RegisterVaccinationRecordCommand toCommand() {
        return new RegisterVaccinationRecordCommand(idCard, idTreatment, idDoctor, applicationDate, notes);
    }
}