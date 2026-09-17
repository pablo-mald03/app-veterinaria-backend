package com.happypets.app_veterinaria_backend.user.application.query.getRoles;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Principal get user roles handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetUserRolesHandler implements RequestHandler<GetUserRolesRequest, GetUserRolesResponse> {

    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    @Override
    public GetUserRolesResponse handle(GetUserRolesRequest request) {

        User user = userRepositoryPort.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + request.getUserId()));

        return new GetUserRolesResponse(user.getId(), user.getUserRegistry(), user.getIdentification(),
                user.getEmail(), new ArrayList<>(user.getRoles()));
    }

    @Override
    public Class<GetUserRolesRequest> getRequestType() {
        return GetUserRolesRequest.class;
    }
}