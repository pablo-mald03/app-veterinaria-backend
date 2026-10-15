package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.RegisterAppointmentCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.RoomOccupiedException;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.VetScheduleConflictException;
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
        Appointment appointmentToCreate = appointmentDTOMapper.toDomain(command.getData());
        // 1. Validar choque de horario del veterinario
        if (appointmentRepositoryPort.isVetBusyAt(
                appointmentToCreate.getUserId(),
                appointmentToCreate.getDate(),
                appointmentToCreate.getHour())) {
            throw new VetScheduleConflictException(
                    "El veterinario seleccionado ya tiene una cita agendada en ese horario.");
        }

        // 2. Validar disponibilidad de la sala
        if (appointmentRepositoryPort.isRoomOccupiedAt(
                appointmentToCreate.getRoomId(),
                appointmentToCreate.getDate(),
                appointmentToCreate.getHour())) {
            throw new RoomOccupiedException(
                    "La sala seleccionada está ocupada en ese horario.");
        }

        appointmentToCreate.setStatus("SCHEDULED");
        Appointment savedAppointment = appointmentRepositoryPort.save(appointmentToCreate);
        return appointmentDTOMapper.toDTO(savedAppointment);
    }

    @Override
    public Class<RegisterAppointmentCommand> getRequestType() {
        return RegisterAppointmentCommand.class;
    }
}
