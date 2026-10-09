package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal update room response dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoomResponseDto {
    private Long id;
    private String name;
    private int number;
}