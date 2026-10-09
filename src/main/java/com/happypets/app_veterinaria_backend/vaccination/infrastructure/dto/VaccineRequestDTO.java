package com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VaccineRequestDTO {

    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String name;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 150, message = "La especie no puede superar los 150 caracteres")
    private String species;

    @NotNull(message = "La cantidad de dosis es obligatoria")
    @Positive(message = "La cantidad de dosis debe ser mayor que cero")
    private Integer dosesRequired;

    @Positive(message = "El intervalo debe ser mayor que cero")
    private Integer intervalDays;

    private boolean status;
}
