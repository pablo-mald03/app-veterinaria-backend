package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.ChangeAppointmentStatusCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeAppointmentStatusHandler implements RequestHandler<ChangeAppointmentStatusCommand, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public AppointmentResponseDTO handle(ChangeAppointmentStatusCommand command) {
        Appointment appointment = repositoryPort.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + command.getId()));

        appointment.setStatus(command.getData().getStatus());

        Appointment savedAppointment = repositoryPort.save(appointment);
        return mapper.toDTO(savedAppointment);
    }

    @Override
    public Class<ChangeAppointmentStatusCommand> getRequestType() {
        return ChangeAppointmentStatusCommand.class;
    }
}
