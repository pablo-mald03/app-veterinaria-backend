package com.happypets.app_veterinaria_backend.role.application.command.create;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.domain.exeptions.InvalidPermissionIdsException;
import com.happypets.app_veterinaria_backend.permissions.domain.port.PermissionRepositoryPort;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.DuplicateRoleAliasException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handler class to create a new role
 *
 */
@Service
@RequiredArgsConstructor
public class CreateRoleHandler implements RequestHandler<CreateRoleRequest, CreateRoleResponse> {

    //Attributes
    private final RoleRepositoryPort roleRepositoryPort;
    private final PermissionRepositoryPort permissionRepositoryPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;

    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CreateRoleResponse handle(CreateRoleRequest request) {

        String normalizedAlias = roleAssignmentPolicyService.normalizeAlias(request.getAlias());

        roleAssignmentPolicyService.validateNotReserved(normalizedAlias, "No puedes crear un rol como este");

        if (roleRepositoryPort.existsByAlias(normalizedAlias)) {
            throw new DuplicateRoleAliasException("Ya existe un rol con alias " + normalizedAlias);
        }

        Set<Long> validIds = permissionRepositoryPort.findExistingIds(request.getPermissionIds());
        if (validIds.size() != request.getPermissionIds().size()) {
            throw new InvalidPermissionIdsException("Uno o más permisos no existen en el catálogo");
        }

        Set<Permission> permissions = permissionRepositoryPort.findByIds(validIds);

        Role role = Role.builder()
                .alias(normalizedAlias)
                .name(request.getName())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        Role saved = roleRepositoryPort.save(role);

        Set<Long> savedPermissionIds = saved.getPermissions().stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        return new CreateRoleResponse(saved.getId(), saved.getAlias(), savedPermissionIds);
    }

    @Override
    public Class<CreateRoleRequest> getRequestType() {
        return CreateRoleRequest.class;
    }
}