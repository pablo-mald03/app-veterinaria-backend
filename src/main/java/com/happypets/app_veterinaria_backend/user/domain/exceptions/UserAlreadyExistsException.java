package com.happypets.app_veterinaria_backend.user.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
