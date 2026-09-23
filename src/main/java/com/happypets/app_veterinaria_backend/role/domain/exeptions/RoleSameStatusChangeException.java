package com.happypets.app_veterinaria_backend.role.domain.exeptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Exception if the user is already disabled
 *
 */
public class RoleSameStatusChangeException extends BusinessRuleException {
    public RoleSameStatusChangeException(String message) {
        super(message);
    }
}
