package com.happypets.app_veterinaria_backend.logs.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.logs.domain.entity.Log;
import com.happypets.app_veterinaria_backend.logs.infrastructure.database.entity.LogEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

/**
 * Principal persistency mapper
 *
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LogEntityMapper {

    /**
     * Method to map the entity to domain
     *
     */
    @Mapping(target = "userId", source = "user", qualifiedByName = "userEntityToUserId")
    @Mapping(target = "userRegistry", source = "user.userRegistry")
    @Mapping(target = "userIdentification", source = "user.identification")
    Log mapToLog(LogEntity entity);

    @Named("userEntityToUserId")
    default Long userEntityToUserId(UserEntity user) {
        return user != null ? user.getId() : null;
    }
}
