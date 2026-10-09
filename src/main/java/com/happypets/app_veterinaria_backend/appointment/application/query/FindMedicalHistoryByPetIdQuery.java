package com.happypets.app_veterinaria_backend.appointment.application.query;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMedicalHistoryByPetIdQuery implements Request<List<AppointmentResponseDTO>> {
    private final Long petId;
}