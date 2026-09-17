package com.happypets.app_veterinaria_backend.user.application.command.register;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.service.RoleAssignmentPolicyService;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.EmailAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.IdentificationAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.password.PasswordEncoderPort;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * Principal handler to register a new user
 *
 */
@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {


    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoderPort passwordHasherPort;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final RoleAssignmentPolicyService roleAssignmentPolicyService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterUserResponse handle(RegisterUserRequest request) {

        if (userRepositoryPort.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya está en uso");
        }
        if (userRepositoryPort.existsByIdentification(request.getIdentification())) {
            throw new IdentificationAlreadyExistsException("Usuario con identificacion ya registrada");
        }
        if (userRepositoryPort.existsByUserRegistry(request.getUserRegistry())) {
            throw new UserAlreadyExistsException("El username ya esta en uso");
        }

        Set<Role> rolesToAssign = roleRepositoryPort.findByAliases(request.getRoleAliases());
        if (rolesToAssign.size() != request.getRoleAliases().size()) {
            throw new RoleNotFoundException("Uno o más roles no existen o están deshabilitados");
        }

        //Validation
        AuthUser authenticatedUser = authenticatedUserPort.getAuthenticatedUser();

        roleAssignmentPolicyService.validate(
                new HashSet<>(authenticatedUser.getRoles()),
                request.getRoleAliases()
        );

        //Encoded password
        String hashedPassword = passwordHasherPort.encode(request.getRawPassword());

        User user = User.builder().identification(request.getIdentification()).name(request.getName())
                .firstName(request.getFirstName()).userRegistry(request.getUserRegistry())
                .phone(request.getPhone())
                .password(hashedPassword)
                .email(request.getEmail()).status(true)
                .roles(rolesToAssign).build();

        User saved = userRepositoryPort.save(user);

        return new RegisterUserResponse(saved.getName(), saved.getEmail());
    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
