package com.happypets.app_veterinaria_backend.appointment.application.query;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;

import java.util.List;

public class FindUpcomingAppointmentsQuery implements Request<List<AppointmentResponseDTO>> {
}
