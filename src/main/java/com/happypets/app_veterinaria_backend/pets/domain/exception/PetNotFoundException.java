package com.happypets.app_veterinaria_backend.pets.domain.exception;

import com.happypets.app_veterinaria_backend.common.application.exception.ResourceNotFoundException;

public class PetNotFoundException extends ResourceNotFoundException {

    public PetNotFoundException(Long id) {
        super("Mascota con el id " + id + " no encontrada");
    }
}
