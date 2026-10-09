package com.happypets.app_veterinaria_backend.room.domain.api;

import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request.CreateRoomRequestDto;
import com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response.CreateRoomResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal rest controller contest interface
 *
 */
public interface RoomRestController {

    ResponseEntity<CreateRoomResponseDto> createRoom(@RequestBody @Valid CreateRoomRequestDto dto);
}
