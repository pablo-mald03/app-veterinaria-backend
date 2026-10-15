package com.happypets.app_veterinaria_backend.appointment.domain.exception;


import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

public class TimeAppointmentRuleException extends BusinessRuleException {
    public TimeAppointmentRuleException(String message) {
        super(message);
    }
}
