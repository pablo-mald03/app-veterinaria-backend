package com.happypets.app_veterinaria_backend.role.domain.exeptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Exception when the role is reserved by the system
 *
 */
public class ReservedRoleAliasException extends ConflictException {
    public ReservedRoleAliasException(String message) {
        super(message);
    }
}
