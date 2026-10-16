package com.happypets.app_veterinaria_backend.payment.domain.exception;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

public class InvalidPaymentStatusException extends BusinessRuleException {
    public InvalidPaymentStatusException(String message) {
        super(message);
    }
}
