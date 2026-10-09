package com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class VaccinationCardNotFoundException extends ResourceNotFoundException {

    public VaccinationCardNotFoundException(Long id) {
        super("Carnet de vacunación no encontrado con id: " + id);
    }
}
