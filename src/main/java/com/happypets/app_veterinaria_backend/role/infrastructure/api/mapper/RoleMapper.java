package com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.application.query.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.GetAllRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.PermissionResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.RoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.RoleSummaryDto;
import com.happypets.app_veterinaria_backend.user.domain.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Principal mapper class for the user layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoleMapper {

    /**
     * Method to transform role to role response dto
     *
     */
    RoleResponseDto toDto(Role role);

    /**
     * Method to transform role response dto to domain role
     *
     */
    List<RoleResponseDto> toDtoList(List<Role> roles);

    /**
     * Method to transform permission response dto to domain permission
     *
     */
    PermissionResponseDto toDto(Permission permission);

    /**
     * Principal mapper to mapp to the role summary dto
     *
     */
    RoleSummaryDto toRoleSummaryDto(Role role);

    /**
     * Default method to transform all the role Response dto from get all role response
     *
     */
    default GetAllRoleResponseDto toGetAllResponseDto(GetAllRoleResponse response) {
        PaginationResult<Role> result = response.getRoles();
        return new GetAllRoleResponseDto(
                toDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

}
