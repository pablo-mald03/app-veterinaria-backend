package com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.user.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.user.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.PermissionEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
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
    @Mapping(target = "users", ignore = true)
    @Mapping(source = "permissions", target = "assignedPermissions")
    RoleEntity mapToRoleEntity(Role role);

    /**
     * Method to map the permission domain to the permission entity (nested with role)
     *
     */
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
    @Mapping(source = "assignedPermissions", target = "permissions")
    Role mapToRole(RoleEntity roleEntity);

    /**
     * Method to map the permission domain to the entity
     *
     */
    Permission mapToPermission(PermissionEntity permissionEntity);
}
