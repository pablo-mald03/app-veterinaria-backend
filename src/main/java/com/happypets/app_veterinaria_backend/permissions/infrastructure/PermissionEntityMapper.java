package com.happypets.app_veterinaria_backend.permissions.infrastructure;

import com.happypets.app_veterinaria_backend.permissions.domain.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Permission entity mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PermissionEntityMapper {

    /**
     * Mapper from entity layer to domain
     *
     */
    Permission toDomain(PermissionEntity entity);

    /**
     * Mapper from domain layer to entity
     *
     */
    @Mapping(target = "roles", ignore = true)
    PermissionEntity toEntity(Permission domain);
}
