package com.happypets.app_veterinaria_backend.appointment.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AppointmentJPARepository extends JpaRepository<AppointmentJPAEntity, Long> {

}
