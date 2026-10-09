package com.happypets.app_veterinaria_backend.room.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get all rooms response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRoomsResponse {
    private PaginationResult<Room> result;
}