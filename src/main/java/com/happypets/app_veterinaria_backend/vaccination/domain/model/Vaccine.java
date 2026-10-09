package com.happypets.app_veterinaria_backend.vaccination.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vaccine {

    private Long idVaccine;
    private String name;
    private String description;
    private String species;
    private Integer dosesRequired;
    private Integer intervalDays;
    private boolean status;
}
