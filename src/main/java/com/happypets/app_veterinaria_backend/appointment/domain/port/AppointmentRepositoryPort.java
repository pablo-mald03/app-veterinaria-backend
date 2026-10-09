package com.happypets.app_veterinaria_backend.appointment.domain.port;

import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    void deleteById(Long id);
}
