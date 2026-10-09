package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.DeleteAppointmentByIdCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.AppointmentNotFoundException;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteAppointmentByIdHandler implements RequestHandler<DeleteAppointmentByIdCommand, Void> {
    private final AppointmentRepositoryPort repositoryPort;

    @Override
    public Void handle(DeleteAppointmentByIdCommand command) {
        repositoryPort.findById(command.getId())
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + command.getId()));

        repositoryPort.deleteById(command.getId());
        return null;
    }

    @Override
    public Class<DeleteAppointmentByIdCommand> getRequestType() {
        return DeleteAppointmentByIdCommand.class;
    }
}
