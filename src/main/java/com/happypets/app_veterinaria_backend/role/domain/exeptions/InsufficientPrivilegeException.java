package com.happypets.app_veterinaria_backend.role.domain.exeptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Principal exception when the user doesn't have the enough permissions
 *
 */
public class InsufficientPrivilegeException extends BusinessRuleException {
    public InsufficientPrivilegeException(String message) {
        super(message);
    }
}
