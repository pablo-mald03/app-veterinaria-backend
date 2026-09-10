package com.happypets.app_veterinaria_backend.auth.application.command.login;

import com.happypets.app_veterinaria_backend.auth.domain.authentication.AuthenticationPort;
import com.happypets.app_veterinaria_backend.auth.domain.authentication.AuthenticationResult;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal login handler class
 *
 */
@Service
@RequiredArgsConstructor
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {

    //Authentication port
    private final AuthenticationPort authenticationPort;

    @Override
    public LoginUserResponse handle(LoginUserRequest request) {
        AuthenticationResult result = authenticationPort.authenticate(
                request.getEmail(),
                request.getPassword()
        );

        return new LoginUserResponse(result.getToken());
    }

    @Override
    public Class<LoginUserRequest> getRequestType() {
        return LoginUserRequest.class;
    }
}
