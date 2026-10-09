package com.happypets.app_veterinaria_backend.vaccination.infrastructure.mapper;

import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.command.update.UpdatePetRequest;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetRequestDTO;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetResponseDTO;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineRequestDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface VaccineMapper {

    VaccineResponseDTO toResponseDTO(Vaccine vaccine);
    CreateVaccineRequest toCreateRequest(VaccineRequestDTO dto);
}
