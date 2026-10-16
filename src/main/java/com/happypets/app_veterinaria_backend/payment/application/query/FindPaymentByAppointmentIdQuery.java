package com.happypets.app_veterinaria_backend.payment.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPaymentByAppointmentIdQuery implements Request<PaymentResponseDTO> {
    private final Long appointmentId;
}
