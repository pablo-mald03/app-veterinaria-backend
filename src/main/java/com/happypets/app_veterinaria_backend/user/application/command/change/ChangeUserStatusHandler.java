package com.happypets.app_veterinaria_backend.user.application.command.change;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.LastAdminCannotBeDisabledException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.SelfDisableNotAllowedException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserSameStatusChangeException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handler class to disable a user
 *
 */
@Service
@RequiredArgsConstructor
public class ChangeUserStatusHandler implements RequestHandler<ChangeUserStatusRequest, ChangeUserStatusResponse> {

    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ChangeUserStatusResponse handle(ChangeUserStatusRequest request) {

        User existing = userRepositoryPort.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + request.getUserId()));

        AuthUser requester = authenticatedUserPort.getAuthenticatedUser();

        if (existing.isStatus() == request.isStatus()) {
            String messageInfo = request.isStatus() ? "activado" : "desactivado";
            throw new UserSameStatusChangeException("El usuario ya esta " + messageInfo);
        }

        if (!request.isStatus()) {

            if (requester.getId().equals(existing.getId().toString())) {
                throw new SelfDisableNotAllowedException("No puedes desactivar tu propia cuenta");
            }

            Set<String> reservedRolesHeld = existing.getRoles().stream().map(Role::getAlias)
                    .filter(roleAssignmentPolicyService::isReserved).collect(Collectors.toSet());

            for (String reservedAlias : reservedRolesHeld) {
                if (userRepositoryPort.countActiveUsersByRoleAlias(reservedAlias) <= 1) {
                    throw new LastAdminCannotBeDisabledException(
                            "No puedes desactivar el último usuario con el rol " + reservedAlias);
                }
            }
        }

        existing.setStatus(request.isStatus());
        User updated = userRepositoryPort.update(existing);

        return new ChangeUserStatusResponse(updated.getId(), updated.isStatus());
    }

    @Override
    public Class<ChangeUserStatusRequest> getRequestType() {
        return ChangeUserStatusRequest.class;
    }
}