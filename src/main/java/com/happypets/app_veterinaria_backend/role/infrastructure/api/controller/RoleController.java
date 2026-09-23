package com.happypets.app_veterinaria_backend.role.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsRequest;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsResponse;
import com.happypets.app_veterinaria_backend.role.application.query.findById.GetRoleByIdRequest;
import com.happypets.app_veterinaria_backend.role.application.query.findById.GetRoleByIdResponse;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.query.rolePermissions.GetRolePermissionsRequest;
import com.happypets.app_veterinaria_backend.role.application.query.rolePermissions.GetRolePermissionsResponse;
import com.happypets.app_veterinaria_backend.role.domain.api.RoleRestController;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.ChangeStatusRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.CreateRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.PatchRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.UpdateRolePermissionsRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.*;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper.RoleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Module to manage the roles")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class RoleController implements RoleRestController {

    /*Mediator*/
    private final Mediator mediator;

    //Mapper
    private final RoleMapper roleMapper;

    @Operation(summary = "Create a new role", description = "Crea un rol con un combo específico de permisos")
    @PostMapping
    public ResponseEntity<CreateRoleResponseDto> createRole(@RequestBody @Valid CreateRoleRequestDto dto) {
        CreateRoleRequest request = roleMapper.toCreateRequestDto(dto);
        CreateRoleResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toCreateResponseDto(response));
    }

    /**
     * Get all roles pagination
     *
     */
    @Operation(summary = "Get all roles", description = "Get all roles with pagination")
    @GetMapping
    public ResponseEntity<GetAllRoleResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery) {
        GetAllRoleRequest getAllRoleRequest = new GetAllRoleRequest(paginationQuery);
        GetAllRoleResponse response = mediator.dispatch(getAllRoleRequest);
        return ResponseEntity.ok(roleMapper.toGetAllResponseDto(response));
    }

    /**
     * Endpoint to get the permissions of any role by id
     *
     */
    @Operation(summary = "Get permissions of a role", description = "El subrecurso que hablamos antes")
    @GetMapping("/{id}/permissions")
    public ResponseEntity<RolePermissionsResponseDto> getRolePermissions(@PathVariable Long id) {
        GetRolePermissionsRequest request = new GetRolePermissionsRequest(id);
        GetRolePermissionsResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toRolePermissionDto(response.getRole()));
    }

    @Operation(summary = "Update role permissions", description = "Reemplaza el combo completo de permisos de un rol")
    @PutMapping("/{id}/permissions")
    public ResponseEntity<UpdateRolePermissionsResponseDto> updatePermissions(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRolePermissionsRequestDto dto) {

        UpdateRolePermissionsRequest request = roleMapper.toUpdatePermissionsRequest(id, dto);
        UpdateRolePermissionsResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toUpdatePermissionsResponseDto(response));
    }

    /**
     * Find role by id endpoint request
     *
     */
    @Operation(summary = "Get role by id", description = "Get complete detail of any role")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDetailResponseDto> getById(@PathVariable Long id) {
        GetRoleByIdRequest request = new GetRoleByIdRequest(id);
        GetRoleByIdResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toRoleDetailResponseDto(response));
    }

    @Operation(summary = "Update role information", description = "Change role information")
    @PatchMapping("/{id}/role")
    public ResponseEntity<PatchRoleResponseDto> updateRole(@PathVariable Long id, @RequestBody @Valid PatchRoleRequestDto dto) {
        PatchRoleRequest request = roleMapper.toPatchRoleRequest(id, dto);
        PatchRoleResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toPatchRoleResponseDto(response));
    }


    @Operation(summary = "Change role status", description = "Change rol status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ChangeStatusRoleResponseDto> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusRoleRequestDto dto) {
        ChangeStatusRoleRequest request = roleMapper.toChangeRoleStatusRequest(id, dto);
        ChangeStatusRoleResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(roleMapper.toChangeRoleStatusResponseDto(response));
    }
}
