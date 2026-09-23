package com.happypets.app_veterinaria_backend.permissions.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.application.GetAllPermissionsRequest;
import com.happypets.app_veterinaria_backend.permissions.application.GetAllPermissionsResponse;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.request.GetAllPermissionQueryDto;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.GetAllPermissionsResponseDto;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.PermissionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Permission mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PermissionMapper {

    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllPermissionsRequest toGetAllPermissionRequest(GetAllPermissionQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Method to map the permission response to the response log dto
     *
     */
    PermissionResponseDto toPermissionResponseDto(Permission permission);

    /**
     * Method to map the permission response lis to the response log dto
     *
     */
    List<PermissionResponseDto> toPermissionResponseDtoList(List<Permission> permissions);

    /**
     * Mapper helper
     *
     */
    default GetAllPermissionsResponseDto toGetAllPermissionsResponseDto(GetAllPermissionsResponse response) {
        PaginationResult<Permission> result = response.getPermissions();
        return new GetAllPermissionsResponseDto(
                toPermissionResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}
