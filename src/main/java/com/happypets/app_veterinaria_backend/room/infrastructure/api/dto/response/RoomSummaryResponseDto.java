package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Room summary response dto class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomSummaryResponseDto {
    private Long id;
    private String name;
    private String location;
    private int number;
    private boolean status;
}