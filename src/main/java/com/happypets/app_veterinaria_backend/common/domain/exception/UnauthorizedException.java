package com.happypets.app_veterinaria_backend.common.domain.exception;

/**
 * Unauthorized exception
 */
public abstract class UnauthorizedException extends ResourceNotFoundException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
