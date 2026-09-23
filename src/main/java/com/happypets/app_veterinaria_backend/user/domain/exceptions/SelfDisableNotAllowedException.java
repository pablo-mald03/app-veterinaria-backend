package com.happypets.app_veterinaria_backend.user.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

/**
 * Exception when the user try to disable himself
 *
 */
public class SelfDisableNotAllowedException extends ConflictException {
    public SelfDisableNotAllowedException(String message) {
        super(message);
    }
}
