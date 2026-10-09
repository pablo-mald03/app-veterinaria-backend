package com.happypets.app_veterinaria_backend.room.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.ChangeRoomStatusRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.CreateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.GetAllRoomQueryDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.UpdateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.*;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal rest controller contest interface
 *
 */
public interface RoomRestController {

    ResponseEntity<CreateRoomResponseDto> createRoom(@RequestBody @Valid CreateRoomRequestDto dto);

    ResponseEntity<GetAllRoomsResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject @Valid GetAllRoomQueryDto queryDto);

    ResponseEntity<RoomDetailResponseDto> getById(@PathVariable Long id);

    ResponseEntity<UpdateRoomResponseDto> updateRoom(@PathVariable Long id, @RequestBody @Valid UpdateRoomRequestDto dto);

    ResponseEntity<ChangeRoomStatusResponseDto> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeRoomStatusRequestDto dto);
}
