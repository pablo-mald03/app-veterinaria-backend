package com.happypets.app_veterinaria_backend.appointment.application.query;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAppointmentByIdQuery implements Request<AppointmentResponseDTO> {
    private final Long id;
}
