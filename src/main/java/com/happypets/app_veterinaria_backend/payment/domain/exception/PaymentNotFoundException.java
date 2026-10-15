package com.happypets.app_veterinaria_backend.payment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

public class PaymentNotFoundException extends ResourceNotFoundException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
