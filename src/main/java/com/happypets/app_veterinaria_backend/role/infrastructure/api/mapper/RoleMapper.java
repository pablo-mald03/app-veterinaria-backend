package com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.PermissionResponseDto;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsRequest;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsResponse;
import com.happypets.app_veterinaria_backend.role.application.query.findById.GetRoleByIdResponse;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.ChangeStatusRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.CreateRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.PatchRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.UpdateRolePermissionsRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     * Principal method to map the update permissions dto request to the command
     *
     */
    @Mapping(target = "roleId", source = "id")
    @Mapping(target = "permissionIds", source = "dto.permissionIds")
    UpdateRolePermissionsRequest toUpdatePermissionsRequest(Long id, UpdateRolePermissionsRequestDto dto);

    /**
     * Principal method to map the update role permissions response to dto response
     *
     */
    UpdateRolePermissionsResponseDto toUpdatePermissionsResponseDto(UpdateRolePermissionsResponse response);

    /**
     * Method to transform disable roles request dto for user to the user status  response dto
     *
     */
    @Mapping(target = "roleId", source = "id")
    ChangeStatusRoleRequest toChangeRoleStatusRequest(Long id, ChangeStatusRoleRequestDto dto);


    /**
     * Principal method to map the disable role response to the dto
     *
     */
    ChangeStatusRoleResponseDto toChangeRoleStatusResponseDto(ChangeStatusRoleResponse response);

    /**
     * Method to update roles request from dto
     *
     */
    @Mapping(target = "roleId", source = "id")
    PatchRoleRequest toPatchRoleRequest(Long id, PatchRoleRequestDto dto);

    /**
     * Principal method to map the patch role response to the dto
     *
     */
    PatchRoleResponseDto toPatchRoleResponseDto(PatchRoleResponse response);

    /**
     * Principal method to map the find by id role response to the dto
     *
     */

    RoleDetailResponseDto toRoleDetailResponseDto(GetRoleByIdResponse response);


    /**
     * Method to transform role to role response dto
     *
     */
    RolePermissionsResponseDto toRolePermissionDto(Role role);

    /**
     * Method to transform role response dto to domain role
     *
     */

    List<RolePermissionsResponseDto> toRolePermissionDtoList(List<Role> roles);

    /**
     * Method to transform permission response dto to domain permission
     *
     */
    PermissionResponseDto toDto(Permission permission);

    /**
     * Principal mapper to mapp to the role summary dto
     *
     */
    RoleSummaryResponseDto toRoleSummaryDto(Role role);

    /**
     * Method to transform role response dto to domain sumary role
     *
     */

    List<RoleSummaryResponseDto> toRoleSummaryDtoList(List<Role> roles);

    /**
     * Method to transform role dto request to role create request
     *
     */
    CreateRoleRequest toCreateRequestDto(CreateRoleRequestDto dto);

    /**
     * Method to transform create the role response to role create response dto
     *
     */
    CreateRoleResponseDto toCreateResponseDto(CreateRoleResponse response);

    /**
     * Default method to transform all the role Response dto from get all role response
     *
     */
    default GetAllRoleResponseDto toGetAllResponseDto(GetAllRoleResponse response) {
        PaginationResult<Role> result = response.getRoles();
        return new GetAllRoleResponseDto(
                toRoleSummaryDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

}
