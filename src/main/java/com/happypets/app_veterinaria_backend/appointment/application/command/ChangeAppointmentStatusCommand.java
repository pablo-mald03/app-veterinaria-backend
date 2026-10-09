package com.happypets.app_veterinaria_backend.appointment.application.command;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentStatusRequestDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeAppointmentStatusCommand implements Request<AppointmentResponseDTO> {
    private final Long id;
    private final AppointmentStatusRequestDTO data;
}
