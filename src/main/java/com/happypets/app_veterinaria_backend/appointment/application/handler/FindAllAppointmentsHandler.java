package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.query.FindAllAppointmentsQuery;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllAppointmentsHandler implements RequestHandler<FindAllAppointmentsQuery, List<AppointmentResponseDTO>> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public List<AppointmentResponseDTO> handle(FindAllAppointmentsQuery query) {
        return repositoryPort.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Class<FindAllAppointmentsQuery> getRequestType() {
        return FindAllAppointmentsQuery.class;
    }
}
