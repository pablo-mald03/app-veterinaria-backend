package com.happypets.app_veterinaria_backend.vaccinationcard.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VaccinationCard {

    private Long idCard;
    private Long idPet;
    private LocalDate creationDate;
    private boolean status;
}
