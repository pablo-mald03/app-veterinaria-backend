package com.happypets.app_veterinaria_backend.auth.application.command.recoverPassword;

import com.happypets.app_veterinaria_backend.auth.domain.authentication.AuthenticationPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
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
    private final AuthenticationPort authenticationPort;

    @Override
    public Void handle(RecoverPasswordRequest request) {

        /*TODO*/
        return null;
    }

    @Override
    public Class<RecoverPasswordRequest> getRequestType() {
        return RecoverPasswordRequest.class;
    }
}
