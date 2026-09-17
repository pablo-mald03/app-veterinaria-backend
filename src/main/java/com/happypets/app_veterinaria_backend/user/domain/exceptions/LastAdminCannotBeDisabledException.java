package com.happypets.app_veterinaria_backend.user.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

public class LastAdminCannotBeDisabledException extends ConflictException {
    public LastAdminCannotBeDisabledException(String message) {
        super(message);
    }
}
