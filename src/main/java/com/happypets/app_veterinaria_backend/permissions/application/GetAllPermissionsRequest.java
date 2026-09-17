package com.happypets.app_veterinaria_backend.permissions.application;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all permission modules request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPermissionsRequest implements Request<GetAllPermissionsResponse> {
    private String module;
    private String action;
    private PaginationQuery paginationQuery;
}