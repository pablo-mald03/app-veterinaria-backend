package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.appointment.domain.model.Appointment;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentDTOMapper {
    Appointment toDomain(AppointmentRequestDTO dto);
    AppointmentResponseDTO toDTO(Appointment domain);

    @Mapping(source = "pet.idPet", target = "petId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    Appointment toDomain(AppointmentJPAEntity entity);
    @Mapping(source = "petId", target = "pet.idPet")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roomId", target = "room.id")
    AppointmentJPAEntity toEntity(Appointment domain);
}
