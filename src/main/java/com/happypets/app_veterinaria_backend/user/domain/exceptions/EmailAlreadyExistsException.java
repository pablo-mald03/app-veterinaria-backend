package com.happypets.app_veterinaria_backend.user.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

public class EmailAlreadyExistsException extends BusinessRuleException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
