package com.happypets.app_veterinaria_backend.user.application.command.assignRole;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Principal update role user handler class
 *
 */
@Service
@RequiredArgsConstructor
public class AssignRolesToUserHandler implements RequestHandler<AssignRolesToUserRequest, AssignRolesToUserResponse> {

    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public AssignRolesToUserResponse handle(AssignRolesToUserRequest request) {

        User targetUser = userRepositoryPort.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + request.getUserId()));

        Set<Role> rolesToAssign = roleRepositoryPort.findByAliases(request.getRoleAliases());
        if (rolesToAssign.size() != request.getRoleAliases().size()) {
            throw new RoleNotFoundException("Uno o mas roles no existen o estan deshabilitados");
        }

        AuthUser requester = authenticatedUserPort.getAuthenticatedUser();
        roleAssignmentPolicyService.validate(
                new HashSet<>(requester.getRoles()),
                request.getRoleAliases()
        );

        targetUser.setRoles(rolesToAssign);
        User updated = userRepositoryPort.update(targetUser);

        Set<String> assignedAliases = updated.getRoles().stream()
                .map(Role::getAlias).collect(Collectors.toSet());

        return new AssignRolesToUserResponse(updated.getId(), assignedAliases);
    }

    @Override
    public Class<AssignRolesToUserRequest> getRequestType() {
        return AssignRolesToUserRequest.class;
    }
}