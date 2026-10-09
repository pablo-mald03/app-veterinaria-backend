package com.happypets.app_veterinaria_backend.room.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.command.patch.ChangeRoomStatusRequest;
import com.happypets.app_veterinaria_backend.room.application.command.patch.ChangeRoomStatusResponse;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.query.getAll.GetAllRoomsRequest;
import com.happypets.app_veterinaria_backend.room.application.query.getAll.GetAllRoomsResponse;
import com.happypets.app_veterinaria_backend.room.application.query.getById.GetRoomByIdResponse;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.filter.RoomFilter;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.ChangeRoomStatusRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.CreateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.GetAllRoomQueryDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.UpdateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.*;
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


    @Mapping(target = "filter", source = "queryDto")
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllRoomsRequest toGetAllRoomsRequest(GetAllRoomQueryDto queryDto, PaginationQuery paginationQuery);

    RoomFilter toRoomFilter(GetAllRoomQueryDto queryDto);

    @Mapping(target = "content", source = "result.content")
    @Mapping(target = "page", source = "result.page")
    @Mapping(target = "size", source = "result.size")
    @Mapping(target = "totalPages", source = "result.totalPages")
    @Mapping(target = "totalElements", source = "result.totalElements")
    GetAllRoomsResponseDto toGetAllRoomsResponseDto(GetAllRoomsResponse response);

    RoomSummaryResponseDto toRoomSummaryResponseDto(Room room);

    @Mapping(target = "roomId", source = "id")
    @Mapping(target = "status", source = "dto.status")
    ChangeRoomStatusRequest toChangeRoomStatusRequest(Long id, ChangeRoomStatusRequestDto dto);

    ChangeRoomStatusResponseDto toChangeRoomStatusResponseDto(ChangeRoomStatusResponse response);
}