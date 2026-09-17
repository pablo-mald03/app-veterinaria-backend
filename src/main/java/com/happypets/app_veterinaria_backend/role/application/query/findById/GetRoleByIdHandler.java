package com.happypets.app_veterinaria_backend.role.application.query.findById;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Principal get role by id handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetRoleByIdHandler implements RequestHandler<GetRoleByIdRequest, GetRoleByIdResponse> {


    //Attributes
    private final RoleRepositoryPort roleRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    public GetRoleByIdResponse handle(GetRoleByIdRequest request) {

        Role role = roleRepositoryPort.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado con ID: " + request.getRoleId()));

        return new GetRoleByIdResponse(role.getId(), role.getAlias(), role.getName(), role.getDescription());
    }

    @Override
    public Class<GetRoleByIdRequest> getRequestType() {
        return GetRoleByIdRequest.class;
    }
}
