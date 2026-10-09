package com.happypets.app_veterinaria_backend.appointment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

public class VetScheduleConflictException extends ConflictException {
    public VetScheduleConflictException(String message) {
        super(message);
    }
}