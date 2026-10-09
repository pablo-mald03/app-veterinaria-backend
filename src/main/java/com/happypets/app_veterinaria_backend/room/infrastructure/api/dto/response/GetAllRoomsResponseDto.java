package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal get all rooms response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRoomsResponseDto {
    private List<RoomSummaryResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}