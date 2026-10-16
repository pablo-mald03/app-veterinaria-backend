package com.happypets.app_veterinaria_backend.payment.domain.port;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findByAppointmentId(Long appointmentId);
    Optional<Payment> findById(Long id);
}
