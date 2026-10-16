package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.payment.application.query.FindPaymentByAppointmentIdQuery;
import com.happypets.app_veterinaria_backend.payment.domain.exception.PaymentNotFoundException;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPaymentByAppointmentIdQueryHandler implements RequestHandler<FindPaymentByAppointmentIdQuery, PaymentResponseDTO> {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentDTOMapper mapper;

    @Override
    public PaymentResponseDTO handle(FindPaymentByAppointmentIdQuery query) {
        Payment payment = paymentRepositoryPort.findByAppointmentId(query.getAppointmentId())
                .orElseThrow(() -> new PaymentNotFoundException("No se encontró una cuenta de cobro para esta cita.") {});

        return mapper.toDTO(payment);
    }

    @Override
    public Class<FindPaymentByAppointmentIdQuery> getRequestType() {
        return FindPaymentByAppointmentIdQuery.class;
    }
}
