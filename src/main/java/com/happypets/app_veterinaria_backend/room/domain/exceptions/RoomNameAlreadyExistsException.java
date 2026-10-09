package com.happypets.app_veterinaria_backend.room.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;


/**
 * Room name already exists exception
 *
 */
public class RoomNameAlreadyExistsException extends ConflictException {

    public RoomNameAlreadyExistsException(String message) {
        super(message);
    }
}
