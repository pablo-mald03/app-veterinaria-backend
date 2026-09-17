package com.happypets.app_veterinaria_backend.common.domain.exception;

public class UserDisabledException extends RuntimeException {
    public UserDisabledException(String message) {
        super(message);
    }
}
