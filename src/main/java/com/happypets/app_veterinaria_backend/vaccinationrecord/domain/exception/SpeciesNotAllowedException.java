package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class SpeciesNotAllowedException extends ResourceNotFoundException {

    public SpeciesNotAllowedException(String treatmentName, String species) {
        super("El tratamiento '" + treatmentName + "' no aplica para la especie '" + species + "'");
    }
}