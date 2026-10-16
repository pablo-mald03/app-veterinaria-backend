package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;
import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;
import com.happypets.app_veterinaria_backend.payment.application.command.ProcessPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.domain.exception.InvalidPaymentStatusException;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProcessPaymentHandler implements RequestHandler<ProcessPaymentCommand, PaymentResponseDTO> {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentDTOMapper mapper;

    @Override
    public PaymentResponseDTO handle(ProcessPaymentCommand command) {
        Payment payment = paymentRepositoryPort.findById(command.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket de cobro no encontrado.") {});

        if (!"PENDING".equals(payment.getStatus())) {
            throw new InvalidPaymentStatusException("Este ticket ya se encuentra en estado " + payment.getStatus());
        }

        // Aplicamos los cambios de facturación
        payment.setStatus("PAID");
        payment.setPaymentMethod(command.getData().getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepositoryPort.save(payment);
        return mapper.toDTO(savedPayment);
    }

    @Override
    public Class<ProcessPaymentCommand> getRequestType() {
        return ProcessPaymentCommand.class;
    }
}
