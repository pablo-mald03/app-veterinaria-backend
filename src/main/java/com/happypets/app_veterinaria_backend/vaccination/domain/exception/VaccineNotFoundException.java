package com.happypets.app_veterinaria_backend.vaccination.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class VaccineNotFoundException extends ResourceNotFoundException {

    public VaccineNotFoundException(Long id) {
        super("Vacuna con el id: " + id + " no encontrada");
    }
}
