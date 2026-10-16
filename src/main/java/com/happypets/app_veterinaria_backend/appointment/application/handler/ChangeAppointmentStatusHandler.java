package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.ChangeAppointmentStatusCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.AppointmentNotFoundException;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.payment.application.command.OpenPaymentCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ChangeAppointmentStatusHandler implements RequestHandler<ChangeAppointmentStatusCommand, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;
    private final Mediator mediator;

    public ChangeAppointmentStatusHandler(
            AppointmentRepositoryPort repositoryPort,
            AppointmentDTOMapper mapper,
            @Lazy Mediator mediator) {
        this.repositoryPort = repositoryPort;
        this.mapper = mapper;
        this.mediator = mediator;
    }

    @Override
    public AppointmentResponseDTO handle(ChangeAppointmentStatusCommand command) {
        Appointment appointment = repositoryPort.findById(command.getId())
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + command.getId()));

        appointment.setStatus(command.getData().getStatus());
        Appointment savedAppointment = repositoryPort.save(appointment);

        // --- INTEGRACION CON PAGOS ---
        // Si la cita pasó a "IN_PROGRESS", abrimos su cuenta de cobro
        if ("IN_PROGRESS".equals(savedAppointment.getStatus())) {
            mediator.dispatch(new OpenPaymentCommand(savedAppointment.getId()));
        }
        return mapper.toDTO(savedAppointment);
    }

    @Override
    public Class<ChangeAppointmentStatusCommand> getRequestType() {
        return ChangeAppointmentStatusCommand.class;
    }
}
