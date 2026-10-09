package com.happypets.app_veterinaria_backend.room.domain.port;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.filter.RoomFilter;

import java.util.Optional;

/**
 * Room repository port
 *
 */
public interface RoomRepositoryPort {

    Room create(Room room);

    Room update(Room room);

    Optional<Room> findById(Long id);

    PaginationResult<Room> findAll(RoomFilter filter, PaginationQuery paginationQuery);
}
