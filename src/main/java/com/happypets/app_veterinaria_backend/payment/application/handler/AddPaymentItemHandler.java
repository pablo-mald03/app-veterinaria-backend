package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.payment.application.command.AddPaymentItemCommand;
import com.happypets.app_veterinaria_backend.payment.domain.exception.InvalidPaymentStatusException;
import com.happypets.app_veterinaria_backend.payment.domain.exception.PaymentNotFoundException;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.model.PaymentItem;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddPaymentItemHandler implements RequestHandler<AddPaymentItemCommand, PaymentResponseDTO> {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentDTOMapper mapper;

    @Override
    public PaymentResponseDTO handle(AddPaymentItemCommand command) {
        // 1. Buscar la cuenta de cobro por el ID de la cita
        Payment payment = paymentRepositoryPort.findByAppointmentId(command.getAppointmentId())
                .orElseThrow(() -> new PaymentNotFoundException("No se encontró una cuenta abierta para esta cita."));

        // 2. Validar que la cuenta no esté ya pagada o cancelada
        if (!"PENDING".equals(payment.getStatus())) {
            throw new InvalidPaymentStatusException("No se pueden agregar cargos a una cuenta que ya está " + payment.getStatus());
        }

        // 3. Crear el nuevo ítem y agregarlo a la cuenta
        PaymentItem newItem = new PaymentItem();
        newItem.setDescription(command.getData().getDescription());
        newItem.setQuantity(command.getData().getQuantity());
        newItem.setUnitPrice(command.getData().getUnitPrice());
        newItem.setReferenceType(command.getData().getReferenceType());
        newItem.setReferenceId(command.getData().getReferenceId());

        // Este metodo autocalcula el subtotal del ítem y actualiza el totalAmount del Payment
        payment.addItem(newItem);

        // 4. Guardar (JPA Cascade se encargará de guardar el ítem nuevo y actualizar el Payment)
        Payment savedPayment = paymentRepositoryPort.save(payment);
        return mapper.toDTO(savedPayment);
    }

    @Override
    public Class<AddPaymentItemCommand> getRequestType() {
        return AddPaymentItemCommand.class;
    }
}
