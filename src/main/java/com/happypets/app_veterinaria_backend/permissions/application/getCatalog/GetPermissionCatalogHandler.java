package com.happypets.app_veterinaria_backend.permissions.application.getCatalog;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.permissions.domain.port.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Get permission catalog handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetPermissionCatalogHandler implements RequestHandler<GetPermissionCatalogRequest, GetPermissionCatalogResponse> {

    //Attributes
    private final PermissionRepositoryPort permissionRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public GetPermissionCatalogResponse handle(GetPermissionCatalogRequest request) {
        List<String> modules = permissionRepositoryPort.findDistinctModules();
        List<String> actions = permissionRepositoryPort.findDistinctActions();

        return new GetPermissionCatalogResponse(modules, actions);
    }

    @Override
    public Class<GetPermissionCatalogRequest> getRequestType() {
        return GetPermissionCatalogRequest.class;
    }
}
