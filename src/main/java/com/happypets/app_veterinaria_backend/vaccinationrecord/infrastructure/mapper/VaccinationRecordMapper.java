package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.mapper;

import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordCommand;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordRequestDTO;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VaccinationRecordMapper {

    RegisterVaccinationRecordCommand toCommand(VaccinationRecordRequestDTO dto);

    VaccinationRecordResponseDTO toResponse(VaccinationRecord record);

    List<VaccinationRecordResponseDTO> toResponseList(List<VaccinationRecord> records);
}
