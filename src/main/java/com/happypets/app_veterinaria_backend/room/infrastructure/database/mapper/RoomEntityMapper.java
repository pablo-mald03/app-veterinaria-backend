package com.happypets.app_veterinaria_backend.room.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.infrastructure.database.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Room entity mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoomEntityMapper {


    /**
     * Method to map the room domain to the entity persistence layer
     *
     */
    RoomEntity toEntity(Room room);


    /**
     * Method to map the room entity persistence layer to the domain
     *
     */
    Room toDomain(RoomEntity roomEntity);
}
