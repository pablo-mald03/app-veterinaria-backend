package com.happypets.app_veterinaria_backend.common.domain.exception;

/**
 * Principal exception to represents when a business rule doesn't allow
 *
 */
public abstract class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
