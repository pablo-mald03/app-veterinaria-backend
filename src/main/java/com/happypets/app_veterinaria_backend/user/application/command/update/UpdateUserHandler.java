package com.happypets.app_veterinaria_backend.user.application.command.update;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.EmailAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.IdentificationAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserAlreadyExistsException;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Principal update user handler class
 *
 */
@Service
@RequiredArgsConstructor
public class UpdateUserHandler implements RequestHandler<UpdateUserRequest, UpdateUserResponse> {

    //Attributes
    private final UserRepositoryPort userRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public UpdateUserResponse handle(UpdateUserRequest request) {

        User existing = userRepositoryPort.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + request.getUserId()));

        if (request.getEmail() != null && !request.getEmail().equals(existing.getEmail())) {
            if (userRepositoryPort.existsByEmailAndIdNot(request.getEmail(), request.getUserId())) {
                throw new EmailAlreadyExistsException("El correo ya está en uso por otro usuario");
            }
            existing.setEmail(request.getEmail());
        }

        if (request.getIdentification() != null && !request.getIdentification().equals(existing.getIdentification())) {
            if (userRepositoryPort.existsByIdentificationAndIdNot(request.getIdentification(), request.getUserId())) {
                throw new IdentificationAlreadyExistsException("La identificacion ya esta en uso por otro usuario");
            }
            existing.setIdentification(request.getIdentification());
        }

        if (request.getUserRegistry() != null && !request.getUserRegistry().equals(existing.getUserRegistry())) {
            if (userRepositoryPort.existsByUserRegistryAndIdNot(request.getUserRegistry(), request.getUserId())) {
                throw new UserAlreadyExistsException("El username ya esta en uso por otro usuario");
            }
            existing.setUserRegistry(request.getUserRegistry());
        }

        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getFirstName() != null) {
            existing.setFirstName(request.getFirstName());
        }
        if (request.getPhone() != null) {
            existing.setPhone(request.getPhone());
        }

        User updated = userRepositoryPort.update(existing);


        return new UpdateUserResponse(updated.getId(), updated.getName(), updated.getEmail());
    }

    @Override
    public Class<UpdateUserRequest> getRequestType() {
        return UpdateUserRequest.class;
    }
}