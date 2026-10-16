package com.happypets.app_veterinaria_backend.payment.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenPaymentCommand implements Request<PaymentResponseDTO> {
    private final Long appointmentId;
}
