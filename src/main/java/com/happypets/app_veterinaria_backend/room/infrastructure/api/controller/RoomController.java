package com.happypets.app_veterinaria_backend.room.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.create.CreateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomRequest;
import com.happypets.app_veterinaria_backend.room.application.command.update.UpdateRoomResponse;
import com.happypets.app_veterinaria_backend.room.application.query.getById.GetRoomByIdRequest;
import com.happypets.app_veterinaria_backend.room.application.query.getById.GetRoomByIdResponse;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.CreateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.UpdateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.CreateRoomResponseDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.RoomDetailResponseDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.UpdateRoomResponseDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.mapper.RoomMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@Tag(name = "Rooms", description = "Module to manage the rooms")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class RoomController {

    /*Mediator*/
    private final Mediator mediator;

    //Mapper
    private final RoomMapper roomMapper;

    /**
     * Endpoint to create a new room
     *
     */
    @Operation(summary = "Create a new room", description = "Create a new room for the clinic")
    @PostMapping
    public ResponseEntity<CreateRoomResponseDto> createRoom(@RequestBody @Valid CreateRoomRequestDto dto) {
        CreateRoomRequest request = roomMapper.toCreateRequest(dto);
        CreateRoomResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roomMapper.toCreateResponseDto(response));
    }

    /**
     * Endpoint to get room by id
     *
     */
    @Operation(summary = "Get room by id", description = "Get complete detail of any room")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailResponseDto> getById(@PathVariable Long id) {
        GetRoomByIdRequest request = new GetRoomByIdRequest(id);
        GetRoomByIdResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roomMapper.toRoomDetailResponseDto(response));
    }

    /**
     * Endpoint to update an existing room
     *
     */
    @Operation(summary = "Update room information", description = "Replace the room name, location, description and number")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateRoomResponseDto> updateRoom(@PathVariable Long id, @RequestBody @Valid UpdateRoomRequestDto dto) {
        UpdateRoomRequest request = roomMapper.toUpdateRequest(id, dto);
        UpdateRoomResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roomMapper.toUpdateResponseDto(response));
    }
}