package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VaccinationRecordResponseDTO {

    private Long idRecord;
    private Long idCard;
    private Long idVaccine;
    private Long idDoctor;
    private Integer doseNumber;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String batchNumber;
    private String notes;
}