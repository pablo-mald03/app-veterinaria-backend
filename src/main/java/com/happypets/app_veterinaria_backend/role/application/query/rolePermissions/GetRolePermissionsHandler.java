package com.happypets.app_veterinaria_backend.role.application.query.rolePermissions;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal get permissions by roles handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetRolePermissionsHandler implements RequestHandler<GetRolePermissionsRequest, GetRolePermissionsResponse> {

    //Attributes
    private final RoleRepositoryPort roleRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    public GetRolePermissionsResponse handle(GetRolePermissionsRequest request) {

        Role role = roleRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new RoleNotFoundException("Rol con id: " + request.getId() + "no encontrado"));

        return new GetRolePermissionsResponse(role);
    }

    @Override
    public Class<GetRolePermissionsRequest> getRequestType() {
        return GetRolePermissionsRequest.class;
    }
}
