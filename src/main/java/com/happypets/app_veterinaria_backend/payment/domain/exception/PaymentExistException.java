package com.happypets.app_veterinaria_backend.payment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ConflictException;

public class PaymentExistException extends ConflictException {
    public PaymentExistException(String message) {
        super(message);
    }
}
