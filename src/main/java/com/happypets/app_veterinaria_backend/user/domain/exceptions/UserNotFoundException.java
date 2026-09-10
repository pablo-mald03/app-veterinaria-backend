package com.happypets.app_veterinaria_backend.user.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception when the client was not found
 *
 */
public class UserNotFoundException extends BusinessRuleException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
