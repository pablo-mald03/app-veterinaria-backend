package com.happypets.app_veterinaria_backend.appointment.application.command;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteAppointmentByIdCommand implements Request<Void> {
    private final Long id;
}
