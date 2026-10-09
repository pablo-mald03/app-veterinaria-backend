package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal room detail response dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDetailResponseDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private int number;
    private boolean status;
}