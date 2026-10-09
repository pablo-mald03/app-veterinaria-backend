package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.UpdateAppointmentDiagnosisCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.VetScheduleConflictException;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAppointmentDiagnosisHandler implements RequestHandler<UpdateAppointmentDiagnosisCommand, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public AppointmentResponseDTO handle(UpdateAppointmentDiagnosisCommand command) {
        Appointment appointment = repositoryPort.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + command.getId()));

        if ("CANCELLED".equals(appointment.getStatus())) {
            throw new VetScheduleConflictException("No se puede agregar un diagnóstico a una cita cancelada.");
        }
        if ("COMPLETED".equals(appointment.getStatus())) {
            throw new VetScheduleConflictException("Esta cita ya fue completada y su historial está cerrado.");
        }

        appointment.setDiagnosis(command.getData().getDiagnosis());
        appointment.setTreatment(command.getData().getTreatment());
        appointment.setCost(command.getData().getCost());

        appointment.setStatus("COMPLETED");

        Appointment savedAppointment = repositoryPort.save(appointment);
        return mapper.toDTO(savedAppointment);
    }

    @Override
    public Class<UpdateAppointmentDiagnosisCommand> getRequestType() {
        return UpdateAppointmentDiagnosisCommand.class;
    }
}
