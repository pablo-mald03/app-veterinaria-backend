package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.UpdateAppointmentCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.AppointmentNotFoundException;
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
public class UpdateAppointmentHandler implements RequestHandler<UpdateAppointmentCommand, AppointmentResponseDTO> {
    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public AppointmentResponseDTO handle(UpdateAppointmentCommand command) {
        Appointment existing = repositoryPort.findById(command.getId())
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + command.getId()));

        boolean vetBusy = repositoryPort.isVetBusyAt(
                command.getData().getUserId(),
                command.getData().getDate(),
                command.getData().getHour()
        ) && !isSameVetSlot(existing, command);

        if (vetBusy) {
            throw new VetScheduleConflictException(
                    "El veterinario seleccionado ya tiene una cita agendada en ese horario.");
        }

        // 3. Validar disponibilidad de la sala (excluyendo la cita actual)
        boolean roomOccupied = repositoryPort.isRoomOccupiedAt(
                command.getData().getRoomId(),
                command.getData().getDate(),
                command.getData().getHour()
        ) && !isSameRoomSlot(existing, command);

        if (roomOccupied) {
            throw new RoomOccupiedException(
                    "La sala seleccionada está ocupada en ese horario.");
        }

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

    private boolean isSameVetSlot(Appointment existing, UpdateAppointmentCommand command) {
        return existing.getUserId().equals(command.getData().getUserId())
                && existing.getDate().equals(command.getData().getDate())
                && existing.getHour().equals(command.getData().getHour());
    }

    private boolean isSameRoomSlot(Appointment existing, UpdateAppointmentCommand command) {
        return existing.getRoomId().equals(command.getData().getRoomId())
                && existing.getDate().equals(command.getData().getDate())
                && existing.getHour().equals(command.getData().getHour());
    }

    @Override
    public Class<UpdateAppointmentCommand> getRequestType() {
        return UpdateAppointmentCommand.class;
    }
}
