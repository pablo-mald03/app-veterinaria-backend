package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.query.FindAppointmentByIdQuery;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAppointmentByIdHandler implements RequestHandler<FindAppointmentByIdQuery, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public AppointmentResponseDTO handle(FindAppointmentByIdQuery query) {
        Appointment appointment = repositoryPort.findById(query.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + query.getId()));
        return mapper.toDTO(appointment);
    }

    @Override
    public Class<FindAppointmentByIdQuery> getRequestType() {
        return FindAppointmentByIdQuery.class;
    }
}