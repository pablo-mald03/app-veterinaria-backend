package com.happypets.app_veterinaria_backend.user.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception when the identification already exists
 *
 */
public class IdentificationAlreadyExistsException extends BusinessRuleException {
    public IdentificationAlreadyExistsException(String message) {
        super(message);
    }
}