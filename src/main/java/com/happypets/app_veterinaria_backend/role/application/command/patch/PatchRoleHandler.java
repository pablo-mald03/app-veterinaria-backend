package com.happypets.app_veterinaria_backend.role.application.command.patch;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.DuplicateRoleAliasException;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.ReservedRoleAliasException;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handler class to update permissions at role
 *
 */
@Service
@RequiredArgsConstructor
public class PatchRoleHandler implements RequestHandler<PatchRoleRequest, PatchRoleResponse> {

    //Services
    private final RoleRepositoryPort roleRepositoryPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PatchRoleResponse handle(PatchRoleRequest request) {
        Role existing = roleRepositoryPort.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado con ID: " + request.getRoleId()));

        if (request.getAlias() != null && !request.getAlias().isBlank()) {
            String normalizedAlias = roleAssignmentPolicyService.normalizeAlias(request.getAlias());

            if (roleAssignmentPolicyService.isReserved(existing.getAlias())) {
                throw new ReservedRoleAliasException("El rol: '" + existing.getAlias() + "' no puede ser modificado desde la aplicacion");
            }

            if (!normalizedAlias.equalsIgnoreCase(existing.getAlias())) {
                roleAssignmentPolicyService.validateNotReserved(normalizedAlias, "No puedes modificar un rol como este");

                if (roleRepositoryPort.existsByAlias(normalizedAlias)) {
                    throw new DuplicateRoleAliasException("Ya existe un rol con alias: " + normalizedAlias);
                }
                existing.setAlias(normalizedAlias);
            }
        }

        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        Role patched = roleRepositoryPort.patch(existing);

        return new PatchRoleResponse(patched.getId(), patched.getAlias());
    }

    @Override
    public Class<PatchRoleRequest> getRequestType() {
        return PatchRoleRequest.class;
    }
}
