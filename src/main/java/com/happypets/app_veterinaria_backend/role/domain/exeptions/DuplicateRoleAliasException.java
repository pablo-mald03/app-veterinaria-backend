package com.happypets.app_veterinaria_backend.role.domain.exeptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Principal exeption class when the role already exists
 *
 */
public class DuplicateRoleAliasException extends BusinessRuleException {
    public DuplicateRoleAliasException(String message) {
        super(message);
    }
}
