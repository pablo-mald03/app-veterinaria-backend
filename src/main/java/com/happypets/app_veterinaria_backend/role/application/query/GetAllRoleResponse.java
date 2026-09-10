package com.happypets.app_veterinaria_backend.role.application.query;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all role response
 *
 */
@Data
@AllArgsConstructor
public class GetAllRoleResponse {
    private PaginationResult<Role> roles;
}