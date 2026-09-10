package com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.user.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Role entity mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR, uses = PermissionEntityMapper.class)
public interface RoleEntityMapper {

    /**
     * Mapper from domain layer to entity
     *
     */
    @Mapping(source = "assignedPermissions", target = "permissions")
    Role toDomain(RoleEntity entity);

    /**
     * Mapper from entity layer to domain
     *
     */
    @Mapping(source = "permissions", target = "assignedPermissions")
    @Mapping(target = "users", ignore = true)
    RoleEntity toEntity(Role domain);

}
