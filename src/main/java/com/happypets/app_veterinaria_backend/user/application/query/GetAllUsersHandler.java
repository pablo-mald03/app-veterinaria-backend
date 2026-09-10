package com.happypets.app_veterinaria_backend.user.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal get all users handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllUsersHandler implements RequestHandler<GetAllUsersRequest, GetAllUsersResponse> {

    //Repository
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public GetAllUsersResponse handle(GetAllUsersRequest request) {
        PaginationResult<User> roles = userRepositoryPort.findAll(request.getPaginationQuery());
        return new GetAllUsersResponse(roles);
    }

    @Override
    public Class<GetAllUsersRequest> getRequestType() {
        return GetAllUsersRequest.class;
    }
}
