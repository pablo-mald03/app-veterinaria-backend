package com.happypets.app_veterinaria_backend.treatment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class TreatmentNotFoundException extends ResourceNotFoundException {

    public TreatmentNotFoundException(Long id) {
        super("Tratamiento con el id: " + id + " no encontrado");
    }
}