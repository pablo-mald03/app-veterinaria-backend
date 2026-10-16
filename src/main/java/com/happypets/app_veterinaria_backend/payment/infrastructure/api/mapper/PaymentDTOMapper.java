package com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.model.PaymentItem;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity.PaymentItemJPAEntity;
import com.happypets.app_veterinaria_backend.payment.infrastructure.database.entity.PaymentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentDTOMapper {

    // Domain <-> DTO
    PaymentResponseDTO toDTO(Payment domain);
    PaymentItemDTO toDTO(PaymentItem domain);

    // Entity -> Domain
    @Mapping(source = "appointment.id", target = "appointmentId")
    Payment toDomain(PaymentJPAEntity entity);
    PaymentItem toDomain(PaymentItemJPAEntity entity);

    // Domain -> Entity
    @Mapping(source = "appointmentId", target = "appointment.id")
    PaymentJPAEntity toEntity(Payment domain);
    PaymentItemJPAEntity toEntity(PaymentItem domain);
}
