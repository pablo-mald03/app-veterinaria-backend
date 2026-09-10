package com.happypets.app_veterinaria_backend.user.application.command.register;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.password.PasswordEncoderPort;
import com.happypets.app_veterinaria_backend.user.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * Principal handler to register a new user
 *
 */
@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {


    /*
     * Principal default role
     * */
    private final String DEFAULT_ROLE = "USER";

    /*
     * Repositories
     * */
    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    /**
     * Principal register user handler
     *
     */
    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {

        if (userRepositoryPort.existByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        Role defaultRole = roleRepositoryPort.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException(
                        "Rol '" + DEFAULT_ROLE + "' no encontrado"));

        User user = User.builder()
                .identification(request.getIdentification())
                .name(request.getName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoderPort.encode(request.getPassword()))
                .roles(Set.of(defaultRole))
                .build();

        User insert = userRepositoryPort.insert(user);
        return new RegisterUserResponse(insert.getName());
    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
