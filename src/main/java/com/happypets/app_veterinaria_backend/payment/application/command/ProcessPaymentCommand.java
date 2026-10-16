package com.happypets.app_veterinaria_backend.payment.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.ProcessPaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessPaymentCommand implements Request<PaymentResponseDTO> {
    private final Long paymentId;
    private final ProcessPaymentRequestDTO data;
}
