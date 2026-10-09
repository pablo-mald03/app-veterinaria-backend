package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal change room status response dto class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeRoomStatusResponseDto {
    private Long id;
    private boolean status;
}