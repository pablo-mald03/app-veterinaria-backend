package com.happypets.app_veterinaria_backend.vaccination.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.domain.entity.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.api.dto.VaccineRequestDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.api.dto.VaccineResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface VaccineMapper {

    VaccineResponseDTO toResponseDTO(Vaccine vaccine);

    CreateVaccineRequest toCreateRequest(VaccineRequestDTO dto);
}
