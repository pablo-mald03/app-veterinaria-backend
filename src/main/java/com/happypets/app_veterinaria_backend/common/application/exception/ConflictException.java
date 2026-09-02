package com.happypets.app_veterinaria_backend.common.application.exception;

/**
 * Principal exception to represents when a conflict occurs
 *
 */
public abstract class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
