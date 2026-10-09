package com.happypets.app_veterinaria_backend.appointment.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentJPARepository extends JpaRepository<AppointmentJPAEntity, Long> {
    List<AppointmentJPAEntity> findByPet_IdPetAndStatusOrderByDateDescHourDesc(Long petId, String status);
    boolean existsByUser_IdAndDateAndHourAndStatusNot(Long userId, LocalDate date, LocalTime hour, String status);
    boolean existsByRoom_IdAndDateAndHourAndStatusNot(Long roomId, LocalDate date, LocalTime hour, String status);
}
