package com.happypets.app_veterinaria_backend.appointment.domain.port;

import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findMedicalHistoryByPetId(Long petId);
    void deleteById(Long id);

    boolean isVetBusyAt(Long vetId, LocalDate date, LocalTime time);
    boolean isRoomOccupiedAt(Long roomId, LocalDate date, LocalTime time);
}
