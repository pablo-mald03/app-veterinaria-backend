package com.happypets.app_veterinaria_backend.role.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.domain.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Principal recover get all roles handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllRoleHandler implements RequestHandler<GetAllRoleRequest, GetAllRoleResponse> {

    //Atributes
    private final RoleRepositoryPort roleRepositoryPort;

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
