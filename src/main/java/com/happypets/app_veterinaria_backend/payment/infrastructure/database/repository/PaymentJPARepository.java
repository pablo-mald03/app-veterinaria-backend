package com.happypets.app_veterinaria_backend.payment.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity.PaymentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJPARepository extends JpaRepository<PaymentJPAEntity, Long> {
    Optional<PaymentJPAEntity> findByAppointment_Id(Long appointmentId);
}
