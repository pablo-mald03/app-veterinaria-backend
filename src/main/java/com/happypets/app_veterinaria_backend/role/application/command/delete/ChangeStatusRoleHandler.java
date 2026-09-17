package com.happypets.app_veterinaria_backend.role.application.command.delete;


import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleSameStatusChangeException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handler class to disable a role
 *
 */
@Service
@RequiredArgsConstructor
public class ChangeStatusRoleHandler implements RequestHandler<ChangeStatusRoleRequest, ChangeStatusRoleResponse> {

    //Attributes
    private final RoleRepositoryPort roleRepositoryPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;
    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public ChangeStatusRoleResponse handle(ChangeStatusRoleRequest request) {

        Role existing = roleRepositoryPort.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + request.getRoleId()));

        roleAssignmentPolicyService.validateNotReserved(existing.getAlias(), "No puedes desactivar el rol.");

        if (existing.isActive() == request.isStatus()) {
            String messageInfo = request.isStatus() ? "activado" : "desactivado";
            throw new RoleSameStatusChangeException("El rol ya esta " + messageInfo);
        }

        existing.setActive(request.isStatus());
        Role updated = roleRepositoryPort.update(existing);

        return new ChangeStatusRoleResponse(updated.getId(), updated.getName(), updated.isActive());
    }

    @Override
    public Class<ChangeStatusRoleRequest> getRequestType() {
        return ChangeStatusRoleRequest.class;
    }
}