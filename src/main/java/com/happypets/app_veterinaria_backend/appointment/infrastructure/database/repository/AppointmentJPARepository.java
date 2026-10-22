package com.happypets.app_veterinaria_backend.appointment.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentJPARepository extends JpaRepository<AppointmentJPAEntity, Long> {
    List<AppointmentJPAEntity> findByPet_IdPetAndStatusOrderByDateDescHourDesc(Long petId, String status);
    boolean existsByUser_IdAndDateAndHourAndStatusNot(Long userId, LocalDate date, LocalTime hour, String status);
    boolean existsByRoom_IdAndDateAndHourAndStatusNot(Long roomId, LocalDate date, LocalTime hour, String status);
    @Query("SELECT a FROM AppointmentJPAEntity a WHERE a.status = 'PENDING' AND (a.date > :today OR (a.date = :today AND a.hour >= :now)) ORDER BY a.date ASC, a.hour ASC")
    List<AppointmentJPAEntity> findUpcomingAppointments(@Param("today") LocalDate today, @Param("now") LocalTime now);
}
