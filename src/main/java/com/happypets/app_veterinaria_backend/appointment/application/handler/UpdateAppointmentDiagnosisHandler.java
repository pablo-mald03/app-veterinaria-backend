package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.command.RegisterAppointmentCommand;
import com.happypets.app_veterinaria_backend.appointment.application.command.UpdateAppointmentDiagnosisCommand;
import com.happypets.app_veterinaria_backend.appointment.domain.exception.VetScheduleConflictException;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.FollowUpRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.payment.application.command.AddPaymentItemCommand;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemRequestDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class UpdateAppointmentDiagnosisHandler implements RequestHandler<UpdateAppointmentDiagnosisCommand, AppointmentResponseDTO> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;
    private final Mediator mediator;

    public UpdateAppointmentDiagnosisHandler(
            AppointmentRepositoryPort repositoryPort,
            AppointmentDTOMapper mapper,
            @Lazy Mediator mediator) {
        this.repositoryPort = repositoryPort;
        this.mapper = mapper;
        this.mediator = mediator;
    }

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

        // Definir si es fin de semana (Sábado o Domingo)
        boolean isWeekend = appointment.getDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                appointment.getDate().getDayOfWeek() == DayOfWeek.SUNDAY;

        // Definir si está fuera del horario laboral (antes de las 08:00 o después de las 18:00)
        boolean isAfterHours = appointment.getHour().isBefore(LocalTime.of(8, 0)) ||
                appointment.getHour().isAfter(LocalTime.of(18, 0));

        if (isWeekend || isAfterHours) {
            PaymentItemRequestDTO surchargeFee = new PaymentItemRequestDTO();
            surchargeFee.setDescription("Recargo por horario extraordinario (Fuera de horario o fin de semana)");
            surchargeFee.setQuantity(1);
            surchargeFee.setUnitPrice(new BigDecimal("100.00"));
            surchargeFee.setReferenceType("SURCHARGE");
            surchargeFee.setReferenceId(savedAppointment.getId());

            mediator.dispatch(new AddPaymentItemCommand(savedAppointment.getId(), surchargeFee));
        }

        // INTEGRACIÓN CON CITAS PERIÓDICAS Y DE SEGUIMIENTO
        if (command.getData().getFollowUps() != null && !command.getData().getFollowUps().isEmpty()) {
            for (FollowUpRequestDTO followUp : command.getData().getFollowUps()) {
                AppointmentRequestDTO futureAppointment = new AppointmentRequestDTO();

                futureAppointment.setPetId(appointment.getPetId());
                futureAppointment.setUserId(appointment.getUserId());
                futureAppointment.setRoomId(appointment.getRoomId());

                futureAppointment.setDate(followUp.getDate());
                futureAppointment.setHour(followUp.getHour());

                futureAppointment.setDescription(
                        followUp.getDescription() != null && !followUp.getDescription().isBlank()
                                ? followUp.getDescription()
                                : "Cita de seguimiento / Tratamiento"
                );

                // Marcamos como seguimiento de la cita actual
                futureAppointment.setAppointmentType("FOLLOW_UP");
                futureAppointment.setParentAppointmentId(savedAppointment.getId());

                // Despachar comando para crear la nueva cita
                mediator.dispatch(new RegisterAppointmentCommand(futureAppointment));
            }
        }
        return mapper.toDTO(savedAppointment);
    }

    @Override
    public Class<UpdateAppointmentDiagnosisCommand> getRequestType() {
        return UpdateAppointmentDiagnosisCommand.class;
    }
}
