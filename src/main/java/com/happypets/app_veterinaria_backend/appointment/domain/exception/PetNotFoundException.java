package com.happypets.app_veterinaria_backend.appointment.domain.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message) {
        super(message);
    }
}


