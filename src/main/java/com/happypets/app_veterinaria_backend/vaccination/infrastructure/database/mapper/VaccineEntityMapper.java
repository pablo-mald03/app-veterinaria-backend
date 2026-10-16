package com.happypets.app_veterinaria_backend.vaccination.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.vaccination.domain.entity.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.database.entity.VaccineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface VaccineEntityMapper {

    Vaccine toDomain(VaccineEntity vaccineEntity);

    VaccineEntity toEntity(Vaccine vaccine);
}
