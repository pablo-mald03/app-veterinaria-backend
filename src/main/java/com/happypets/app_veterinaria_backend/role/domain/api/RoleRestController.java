package com.happypets.app_veterinaria_backend.role.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.GetAllRoleResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * Role rest controller contest
 *
 */
public interface RoleRestController {

    ResponseEntity<GetAllRoleResponseDto> getAll(PaginationQuery paginationQuery);

}
