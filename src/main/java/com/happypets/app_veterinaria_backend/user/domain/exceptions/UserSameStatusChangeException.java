package com.happypets.app_veterinaria_backend.user.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception if the user is already disabled
 *
 */
public class UserSameStatusChangeException extends BusinessRuleException {
    public UserSameStatusChangeException(String message) {
        super(message);
    }
}
