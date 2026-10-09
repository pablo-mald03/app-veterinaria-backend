package com.happypets.app_veterinaria_backend.room.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal class when the resource was not found
 *
 */
public class RoomNotFoundException extends ResourceNotFoundException {

    public RoomNotFoundException(String message) {
        super(message);
    }
}
