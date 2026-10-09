package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.database;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.persistence.VaccinationCardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccinationCardEntityMapper {

    VaccinationCardEntity toEntity(VaccinationCard card);

    VaccinationCard toDomain(VaccinationCardEntity entity);
}
