package com.happypets.app_veterinaria_backend.user.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception when the password is invalid
 *
 */
public class InvalidPasswordRecoveryOperation extends BusinessRuleException {
    public InvalidPasswordRecoveryOperation(String message) {
        super(message);
    }
}
