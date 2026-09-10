package com.happypets.app_veterinaria_backend.user.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

public class UserAlreadyExistsException extends BusinessRuleException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
