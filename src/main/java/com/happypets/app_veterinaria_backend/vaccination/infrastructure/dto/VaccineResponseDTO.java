package com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VaccineResponseDTO {

    private Long idVaccine;
    private String name;
    private String description;
    private String species;
    private Integer dosesRequired;
    private Integer intervalDays;
    private boolean status;
}
