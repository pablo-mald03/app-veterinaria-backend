package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.UpdateAppointmentDiagnosisCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.VetScheduleConflictException;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;
import com.happypets.app_veterinaria_backend.payment.application.command.AddPaymentItemCommand;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateAppointmentDiagnosisHandler implements RequestHandler<UpdateAppointmentDiagnosisCommand, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;
    private final Mediator mediator;

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

        // --- INTEGRACIÓN CON PAGOS ---
        // Crear el cargo por la consulta
        PaymentItemRequestDTO consultationFee = new PaymentItemRequestDTO();
        consultationFee.setDescription("Consulta Veterinaria General");
        consultationFee.setQuantity(1);
        consultationFee.setUnitPrice(appointment.getCost()); // O el precio que venga del DTO/Base de datos
        consultationFee.setReferenceType("CONSULTATION");
        consultationFee.setReferenceId(savedAppointment.getId());

        mediator.dispatch(new AddPaymentItemCommand(savedAppointment.getId(), consultationFee));
        return mapper.toDTO(savedAppointment);
    }

    @Override
    public Class<UpdateAppointmentDiagnosisCommand> getRequestType() {
        return UpdateAppointmentDiagnosisCommand.class;
    }
}
