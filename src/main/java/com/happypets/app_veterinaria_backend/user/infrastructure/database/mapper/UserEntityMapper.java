package com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.mapper.RoleEntityMapper;
import com.happypets.app_veterinaria_backend.user.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.PermissionEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

/**
 * User entity mapper
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleEntityMapper.class, PermissionEntityMapper.class}
)
public interface UserEntityMapper {

    /**
     * Method to map the domain to entity but ignore the UserDetails
     *
     */
    @Mapping(target = "authorities", ignore = true)
    UserEntity mapToUserEntity(User user);

    /**
     * Method to map the role domain to the role entity (nested with user)
     *
     */
    @Named("roleToEntity")
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "assignedPermissions", source = "permissions", qualifiedByName = "permissionToEntity")
    RoleEntity mapToRoleEntity(Role role);

    /**
     * Method to map the permission domain to the permission entity (nested with role)
     *
     */
    @Named("permissionToEntity")
    @Mapping(target = "roles", ignore = true)
    PermissionEntity mapToPermissionEntity(Permission permission);

    /**
     * Method to map the user domain to the entity
     *
     */
    User mapToUser(UserEntity userEntity);

    /**
     * Method to map the role domain to the entity
     *
     */
    @Named("roleToDomain")
    @Mapping(source = "assignedPermissions", target = "permissions", qualifiedByName = "permissionToDomain")
    Role mapToRole(RoleEntity roleEntity);

    /**
     * Method to map the permission domain to the entity
     *
     */
    @Named("permissionToDomain")
    Permission mapToPermission(PermissionEntity permissionEntity);
}
