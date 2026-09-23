package com.happypets.app_veterinaria_backend.permissions.application;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all permission modules response class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPermissionsResponse {
    private PaginationResult<Permission> permissions;
}