package com.happypets.app_veterinaria_backend.role.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get all role request
 *
 */
@Data
@AllArgsConstructor
public class GetAllRoleRequest implements Request<GetAllRoleResponse> {

    private PaginationQuery paginationQuery;
}
