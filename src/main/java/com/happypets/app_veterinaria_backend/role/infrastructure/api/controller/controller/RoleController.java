package com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.role.application.query.GetAllRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.query.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.role.domain.api.RoleRestController;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.dto.GetAllRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper.RoleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Module to manage the roles")
@RequiredArgsConstructor
public class RoleController implements RoleRestController {

    /*Mediator*/
    private final Mediator mediator;

    //Mapper
    private final RoleMapper roleMapper;

    /**
     * Get all roles pagination
     *
     */
    @GetMapping
    public ResponseEntity<GetAllRoleResponseDto> getAll(PaginationQuery paginationQuery) {
        GetAllRoleRequest getAllRoleRequest = new GetAllRoleRequest(paginationQuery);
        GetAllRoleResponse response = mediator.dispatch(getAllRoleRequest);
        return ResponseEntity.ok(roleMapper.toGetAllResponseDto(response));
    }


}
