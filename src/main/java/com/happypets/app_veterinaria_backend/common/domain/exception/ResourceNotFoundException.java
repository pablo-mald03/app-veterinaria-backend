package com.happypets.app_veterinaria_backend.common.domain.exception;

/**
 * Principal exception to represents when a resource or entity was not found
 *
 */
public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
