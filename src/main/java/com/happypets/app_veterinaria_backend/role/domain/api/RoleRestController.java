package com.happypets.app_veterinaria_backend.role.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.ChangeStatusRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.CreateRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.PatchRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.UpdateRolePermissionsRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.*;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Role rest controller contest
 *
 */
public interface RoleRestController {

    ResponseEntity<CreateRoleResponseDto> createRole(@RequestBody @Valid CreateRoleRequestDto dto);

    ResponseEntity<GetAllRoleResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery);

    ResponseEntity<RolePermissionsResponseDto> getRolePermissions(@PathVariable Long id);

    ResponseEntity<UpdateRolePermissionsResponseDto> updatePermissions(@PathVariable Long id, @RequestBody @Valid UpdateRolePermissionsRequestDto dto);

    ResponseEntity<RoleDetailResponseDto> getById(@PathVariable Long id);

    ResponseEntity<PatchRoleResponseDto> updateRole(@PathVariable Long id, @RequestBody @Valid PatchRoleRequestDto dto);

    ResponseEntity<ChangeStatusRoleResponseDto> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusRoleRequestDto dto);

}