package com.happypets.app_veterinaria_backend.user.application.query.getProfile;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal get profile handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetProfileHandler implements RequestHandler<GetProfileRequest, GetProfileResponse> {

    private final AuthenticatedUserPort authenticatedUserPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public GetProfileResponse handle(GetProfileRequest request) {
        Long uuid = authenticatedUserPort.getUserId();

        User userResult = userRepositoryPort.findById(uuid).orElseThrow(() -> new UserNotFoundException(" El perfil del usuario con ID: " + uuid + " no fue encontrado"));

        return new GetProfileResponse(userResult);
    }

    @Override
    public Class<GetProfileRequest> getRequestType() {
        return GetProfileRequest.class;
    }
}
