package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.RegisterAppointmentCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterAppointmentHandler implements RequestHandler<RegisterAppointmentCommand, AppointmentResponseDTO> {
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final AppointmentDTOMapper appointmentDTOMapper;

    @Override
    public AppointmentResponseDTO handle(RegisterAppointmentCommand command) {
        Appointment newAppointment = appointmentDTOMapper.toDomain(command.getData());
        newAppointment.setStatus("SCHEDULED");

        Appointment savedAppointment = appointmentRepositoryPort.save(newAppointment);
        return appointmentDTOMapper.toDTO(savedAppointment);
    }

    @Override
    public Class<RegisterAppointmentCommand> getRequestType() {
        return RegisterAppointmentCommand.class;
    }
}
