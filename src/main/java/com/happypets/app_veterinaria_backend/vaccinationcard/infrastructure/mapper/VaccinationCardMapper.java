package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.mapper;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.dto.VaccinationCardResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccinationCardMapper {

    VaccinationCardResponseDTO toResponse(VaccinationCard card);

}
