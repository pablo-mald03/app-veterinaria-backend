package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.database;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence.VaccinationRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccinationRecordEntityMapper {

    VaccinationRecordEntity toEntity(VaccinationRecord record);

    VaccinationRecord toDomain(VaccinationRecordEntity entity);

}
