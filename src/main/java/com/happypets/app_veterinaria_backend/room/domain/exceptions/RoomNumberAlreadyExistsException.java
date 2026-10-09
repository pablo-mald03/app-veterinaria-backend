package com.happypets.app_veterinaria_backend.room.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Room number already exists exception
 *
 */
public class RoomNumberAlreadyExistsException extends ConflictException {

    public RoomNumberAlreadyExistsException(String message) {
        super(message);
    }
}
