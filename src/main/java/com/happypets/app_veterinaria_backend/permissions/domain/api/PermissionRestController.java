package com.happypets.app_veterinaria_backend.permissions.domain.api;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.request.GetAllPermissionQueryDto;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.GetAllPermissionsResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

/**
 * Principal Permission controller contest
 *
 */
public interface PermissionRestController {

    ResponseEntity<GetAllPermissionsResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllPermissionQueryDto queryDto);
}
