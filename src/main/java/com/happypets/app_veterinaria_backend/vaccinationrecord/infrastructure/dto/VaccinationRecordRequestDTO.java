package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VaccinationRecordRequestDTO {

    @NotNull
    private Long idCard;

    @NotNull
    private Long idVaccine;

    @NotNull
    private Long idDoctor;

    @NotNull
    private LocalDate applicationDate;

    private String batchNumber;

    private String notes;
}