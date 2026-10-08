package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.UpdateAppointmentCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAppointmentHandler implements RequestHandler<UpdateAppointmentCommand, AppointmentResponseDTO> {
    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public AppointmentResponseDTO handle(UpdateAppointmentCommand command) {
        Appointment existing = repositoryPort.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + command.getId()));

        existing.setPetId(command.getData().getPetId());
        existing.setUserId(command.getData().getUserId());
        existing.setRoomId(command.getData().getRoomId());
        existing.setDate(command.getData().getDate());
        existing.setHour(command.getData().getHour());
        existing.setDescription(command.getData().getDescription());
        existing.setDiagnosis(command.getData().getDiagnosis());

        Appointment updated = repositoryPort.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public Class<UpdateAppointmentCommand> getRequestType() {
        return UpdateAppointmentCommand.class;
    }
}
