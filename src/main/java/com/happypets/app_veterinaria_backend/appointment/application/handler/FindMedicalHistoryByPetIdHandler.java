package com.happypets.app_veterinaria_backend.appointment.application.handler;

import com.happypets.app_veterinaria_backend.appointment.application.query.FindMedicalHistoryByPetIdQuery;
import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.domain.port.AppointmentRepositoryPort;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper.AppointmentDTOMapper;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindMedicalHistoryByPetIdHandler implements RequestHandler<FindMedicalHistoryByPetIdQuery, List<AppointmentResponseDTO>> {

    private final AppointmentRepositoryPort repositoryPort;
    private final AppointmentDTOMapper mapper;

    @Override
    public List<AppointmentResponseDTO> handle(FindMedicalHistoryByPetIdQuery query) {
        List<Appointment> history = repositoryPort.findMedicalHistoryByPetId(query.getPetId());

        return history.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public Class<FindMedicalHistoryByPetIdQuery> getRequestType() {
        return FindMedicalHistoryByPetIdQuery.class;
    }
}
