package com.happypets.app_veterinaria_backend.role.application.query.getAll;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Principal recover get all roles handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllRoleHandler implements RequestHandler<GetAllRoleRequest, GetAllRoleResponse> {

    //Attributes
    private final RoleRepositoryPort roleRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    public GetAllRoleResponse handle(GetAllRoleRequest request) {
        PaginationResult<Role> roles = roleRepositoryPort.findAll(request.getPaginationQuery());

        return new GetAllRoleResponse(roles);
    }


    @Override
    public Class<GetAllRoleRequest> getRequestType() {
        return GetAllRoleRequest.class;
    }
}