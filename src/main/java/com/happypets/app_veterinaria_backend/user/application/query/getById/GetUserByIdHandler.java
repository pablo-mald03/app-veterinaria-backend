package com.happypets.app_veterinaria_backend.user.application.query.getById;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Principal get user by id handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetUserByIdHandler implements RequestHandler<GetUserByIdRequest, GetUserByIdResponse> {


    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    public GetUserByIdResponse handle(GetUserByIdRequest request) {

        User user = userRepositoryPort.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + request.getUserId()));

        return new GetUserByIdResponse(user.getId(), user.getIdentification(), user.getName(),
                user.getFirstName(), user.getPhone(), user.getUserRegistry(),
                user.getEmail(), user.isStatus(), user.getRoles());
    }

    @Override
    public Class<GetUserByIdRequest> getRequestType() {
        return GetUserByIdRequest.class;
    }
}
