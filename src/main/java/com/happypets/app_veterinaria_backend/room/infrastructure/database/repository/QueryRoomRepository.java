package com.happypets.app_veterinaria_backend.room.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.room.infrastructure.database.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Principal repository for the room
 *
 */
@Repository
public interface QueryRoomRepository extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity> {
    
}