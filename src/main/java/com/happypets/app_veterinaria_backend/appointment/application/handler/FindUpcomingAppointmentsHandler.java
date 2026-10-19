package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.query.FindUpcomingAppointmentsQuery;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindUpcomingAppointmentsHandler implements RequestHandler<FindUpcomingAppointmentsQuery, List<AppointmentResponseDTO>> {
    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public List<AppointmentResponseDTO> handle(FindUpcomingAppointmentsQuery query) {
        // 1. Obtener la fecha y hora exacta en Guatemala
        ZoneId guatemalaZone = ZoneId.of("America/Guatemala");
        LocalDate today = LocalDate.now(guatemalaZone);
        LocalTime now = LocalTime.now(guatemalaZone);

        // 2. Buscar en la base de datos las citas pendientes a partir de este momento
        List<Appointment> upcomingAppointments = repositoryPort.findUpcomingAppointments(today, now);

        // 3. Mapear la lista de entidades de dominio a DTOs
        return upcomingAppointments.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Class<FindUpcomingAppointmentsQuery> getRequestType() {
        return FindUpcomingAppointmentsQuery.class;
    }
}
