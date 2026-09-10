package com.happypets.app_veterinaria_backend.pets.infrastructure.database;

import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.infrastructure.persitence.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PetEntityMapper {

    Pet toDomain(PetEntity petEntity);
    PetEntity toEntity(Pet pet);
}
