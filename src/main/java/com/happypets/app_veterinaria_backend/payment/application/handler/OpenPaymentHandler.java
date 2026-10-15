package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.payment.application.command.OpenPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.domain.exception.PaymentExistException;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class OpenPaymentHandler implements RequestHandler<OpenPaymentCommand, PaymentResponseDTO> {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentDTOMapper mapper;

    @Override
    public PaymentResponseDTO handle(OpenPaymentCommand command) {
        // Validar que la cita no tenga ya una cuenta abierta
        if (paymentRepositoryPort.findByAppointmentId(command.getAppointmentId()).isPresent()) {
            throw new PaymentExistException("Ya existe una cuenta de cobro para esta cita.");
        }

        Payment newPayment = new Payment();
        newPayment.setAppointmentId(command.getAppointmentId());
        newPayment.setStatus("PENDING");
        newPayment.setTotalAmount(BigDecimal.ZERO);
        newPayment.setItems(new ArrayList<>()); // Inicializar la lista vacía

        Payment savedPayment = paymentRepositoryPort.save(newPayment);
        return mapper.toDTO(savedPayment);
    }

    @Override
    public Class<OpenPaymentCommand> getRequestType() {
        return OpenPaymentCommand.class;
    }
}
