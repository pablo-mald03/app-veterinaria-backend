package com.happypets.app_veterinaria_backend.pets.infrastructure.mapper;

import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.command.update.UpdatePetRequest;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetRequestDTO;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetResponseDTO;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.infrastructure.persitence.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PetMapper {

    PetResponseDTO toResponseDTO(Pet pet);
    CreatePetRequest toCreateRequest(PetRequestDTO dto);
    UpdatePetRequest toUpdateRequest(Long idPet, PetRequestDTO petRequestDTO);
}
