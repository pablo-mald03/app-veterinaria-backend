package com.happypets.app_veterinaria_backend.appointment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class AppointmentNotFoundException extends ResourceNotFoundException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}