package com.happypets.app_veterinaria_backend.payment.infrastructure.database.adapter;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity.PaymentJPAEntity;
import com.happypets.app_veterinaria_backend.payment.infrastructure.database.repository.PaymentJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {
    private final PaymentJPARepository repository;
    private final PaymentDTOMapper mapper;

    @Override
    public Payment save(Payment payment) {
        PaymentJPAEntity entity = mapper.toEntity(payment);

        // Sincronizar la relación bidireccional para JPA antes de guardar
        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> item.setPayment(entity));
        }

        PaymentJPAEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findByAppointmentId(Long appointmentId) {
        return repository.findByAppointment_Id(appointmentId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}
