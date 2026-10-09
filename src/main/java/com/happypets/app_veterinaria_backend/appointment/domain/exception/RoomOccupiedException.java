package com.happypets.app_veterinaria_backend.appointment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

public class RoomOccupiedException extends ConflictException {
    public RoomOccupiedException(String message) {
        super(message);
    }
}