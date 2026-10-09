package com.happypets.app_veterinaria_backend.appointment.infrastructure.database.adapter;

import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.repository.AppointmentJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort{
    private final AppointmentJPARepository jpaRepository;
    private final AppointmentDTOMapper mapper;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentJPAEntity entity = mapper.toEntity(appointment);
        AppointmentJPAEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
