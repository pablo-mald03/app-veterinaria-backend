package com.happypets.app_veterinaria_backend.user.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get all users request
 *
 */
@Data
@AllArgsConstructor
public class GetAllUsersRequest implements Request<GetAllUsersResponse> {

    private PaginationQuery paginationQuery;
}
