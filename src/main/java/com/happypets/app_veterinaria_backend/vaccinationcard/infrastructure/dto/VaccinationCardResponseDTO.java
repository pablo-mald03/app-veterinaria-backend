package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VaccinationCardResponseDTO {

    private Long idCard;
    private Long idPet;
    private LocalDate creationDate;
    private boolean status;
}
