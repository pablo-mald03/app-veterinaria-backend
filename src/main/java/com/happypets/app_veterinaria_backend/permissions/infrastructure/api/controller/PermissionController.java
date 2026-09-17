package com.happypets.app_veterinaria_backend.permissions.infrastructure.api.controller;


import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.permissions.application.GetAllPermissionsRequest;
import com.happypets.app_veterinaria_backend.permissions.application.GetAllPermissionsResponse;
import com.happypets.app_veterinaria_backend.permissions.domain.api.PermissionRestController;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.request.GetAllPermissionQueryDto;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.GetAllPermissionsResponseDto;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.mapper.PermissionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Principal permission controller class
 *
 */
@RestController
@RequestMapping("/permissions")
@Tag(name = "Permissions", description = "Module to manage the permissions at the application")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class PermissionController implements PermissionRestController {

    //Mediator
    private final Mediator mediator;

    private final PermissionMapper permissionMapper;

    /**
     * Get all permissions with pageable params and filters endpoint
     *
     */
    @Operation(summary = "Get all permissions", description = "Get all pageable permissions,with filters by module and actions")
    @GetMapping
    @Override
    public ResponseEntity<GetAllPermissionsResponseDto> getAll(PaginationQuery paginationQuery, GetAllPermissionQueryDto queryDto) {
        GetAllPermissionsRequest request = permissionMapper.toGetAllPermissionRequest(queryDto, paginationQuery);
        GetAllPermissionsResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(permissionMapper.toGetAllPermissionsResponseDto(response));
    }
}
