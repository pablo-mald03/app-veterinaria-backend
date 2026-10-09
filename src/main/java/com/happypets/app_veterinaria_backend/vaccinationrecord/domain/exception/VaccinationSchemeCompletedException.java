package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class VaccinationSchemeCompletedException extends ResourceNotFoundException {

    public VaccinationSchemeCompletedException(Long idVaccine) {
        super("El esquema de la vacuna con id " + idVaccine + " ya está completo para este carnet");
    }

}
