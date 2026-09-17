package com.happypets.app_veterinaria_backend.role.application.command.update;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.domain.exeptions.InvalidPermissionIdsException;
import com.happypets.app_veterinaria_backend.permissions.domain.port.PermissionRepositoryPort;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Handler class to update permissions at role
 *
 */
@Service
@RequiredArgsConstructor
public class UpdateRolePermissionsHandler implements RequestHandler<UpdateRolePermissionsRequest, UpdateRolePermissionsResponse> {

    //Services
    private final RoleRepositoryPort roleRepositoryPort;
    private final PermissionRepositoryPort permissionRepositoryPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;
    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public UpdateRolePermissionsResponse handle(UpdateRolePermissionsRequest request) {

        Role existing = roleRepositoryPort.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + request.getRoleId()));

        roleAssignmentPolicyService.validateNotReserved(existing.getAlias(), "No puedes editar los permisos");

        Set<Long> validIds = permissionRepositoryPort.findExistingIds(request.getPermissionIds());
        if (validIds.size() != request.getPermissionIds().size()) {
            throw new InvalidPermissionIdsException("Uno o más permisos no existen en el catálogo");
        }

        Set<Permission> newPermissions = permissionRepositoryPort.findByIds(validIds);

        existing.setPermissions(newPermissions);
        Role updated = roleRepositoryPort.update(existing);

        Set<Long> updatedIds = updated.getPermissions().stream()
                .map(Permission::getId).collect(Collectors.toSet());

        return new UpdateRolePermissionsResponse(updated.getId(), updated.getAlias(), updatedIds);
    }

    @Override
    public Class<UpdateRolePermissionsRequest> getRequestType() {
        return UpdateRolePermissionsRequest.class;
    }
}
