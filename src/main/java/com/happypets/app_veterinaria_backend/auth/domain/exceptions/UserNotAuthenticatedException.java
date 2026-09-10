package com.happypets.app_veterinaria_backend.auth.domain.exceptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.UnauthorizedException;

/**
 * Principal exception when the user is not authenticated
 *
 */
public class UserNotAuthenticatedException extends UnauthorizedException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
