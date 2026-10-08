package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentDTOMapper {
    Appointment toDomain(AppointmentRequestDTO dto);
    AppointmentResponseDTO toDTO(Appointment domain);

    Appointment toDomain(AppointmentJPAEntity entity);
    AppointmentJPAEntity toEntity(Appointment domain);
}
