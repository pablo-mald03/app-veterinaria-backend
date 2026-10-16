package com.happypets.app_veterinaria_backend.payment.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemRequestDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPaymentItemCommand implements Request<PaymentResponseDTO> {
    private final Long appointmentId;
    private final PaymentItemRequestDTO data;
}
