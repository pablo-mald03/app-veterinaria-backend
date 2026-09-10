package com.happypets.app_veterinaria_backend.user.application.command.recoverPassword;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.InvalidPasswordRecoveryOperation;
import com.happypets.app_veterinaria_backend.user.domain.password.PasswordEncoderPort;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal recover password handler class
 *
 */
@Service
@RequiredArgsConstructor
public class RecoverPasswordHandler implements RequestHandler<RecoverPasswordRequest, Void> {

    //Atributes
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoder;


    @Override
    public Void handle(RecoverPasswordRequest request) {

        User user = userRepositoryPort
                .findByEmailAndIdentification(request.getEmail(), request.getDpi())
                .orElseThrow(() -> new InvalidPasswordRecoveryOperation("Credenciales invalidas"));

        if (!request.getPassword().trim().equals(request.getConfirmationPassword().trim())) {
            throw new InvalidPasswordRecoveryOperation("La contraseña no coincide");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepositoryPort.update(user);

        return null;
    }

    @Override
    public Class<RecoverPasswordRequest> getRequestType() {
        return RecoverPasswordRequest.class;
    }
}
