package com.happypets.app_veterinaria_backend.auth.application.query;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Authentication check Handler class
 *
 */
@Service
@RequiredArgsConstructor
public class AuthVerifyUserHandler implements RequestHandler<AuthVerifyUserRequest, AuthVerifyUserResponse> {

    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    public AuthVerifyUserResponse handle(AuthVerifyUserRequest request) {
        AuthUser authUser = authenticatedUserPort.getAuthenticatedUser();

        return new AuthVerifyUserResponse(authUser);
    }

    @Override
    public Class<AuthVerifyUserRequest> getRequestType() {
        return AuthVerifyUserRequest.class;
    }
}
