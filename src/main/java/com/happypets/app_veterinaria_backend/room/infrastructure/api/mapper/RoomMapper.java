package com.happypets.app_veterinaria_backend.room.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.query.getById.GetRoomByIdResponse;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.CreateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.UpdateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.CreateRoomResponseDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.RoomDetailResponseDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.UpdateRoomResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Room mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoomMapper {

    /**
     * Mapper to create room request
     *
     */
    CreateRoomRequest toCreateRequest(CreateRoomRequestDto dto);

    /**
     * Mapper to create response dto
     *
     */
    CreateRoomResponseDto toCreateResponseDto(CreateRoomResponse response);


    @Mapping(target = "roomId", source = "id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "number", source = "dto.number")
    UpdateRoomRequest toUpdateRequest(Long id, UpdateRoomRequestDto dto);

    UpdateRoomResponseDto toUpdateResponseDto(UpdateRoomResponse response);

    RoomDetailResponseDto toRoomDetailResponseDto(GetRoomByIdResponse response);
}