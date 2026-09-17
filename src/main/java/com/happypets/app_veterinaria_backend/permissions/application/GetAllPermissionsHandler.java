package com.happypets.app_veterinaria_backend.permissions.application;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.domain.filter.PermissionFilter;
import com.happypets.app_veterinaria_backend.permissions.domain.port.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Get all permission handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllPermissionsHandler implements RequestHandler<GetAllPermissionsRequest, GetAllPermissionsResponse> {

    //Attributes
    private final PermissionRepositoryPort permissionRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    public GetAllPermissionsResponse handle(GetAllPermissionsRequest request) {
        PermissionFilter filter = PermissionFilter.builder()
                .module(request.getModule())
                .action(request.getAction())
                .build();

        PaginationResult<Permission> permissions = permissionRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllPermissionsResponse(permissions);
    }

    @Override
    public Class<GetAllPermissionsRequest> getRequestType() {
        return GetAllPermissionsRequest.class;
    }
}
