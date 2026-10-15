package com.happypets.app_veterinaria_backend.auth.application.login;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal login request class
 *
 */
@Data
@AllArgsConstructor
public class LoginUserRequest implements Request<LoginUserResponse>, AuditableRequest {

    private String email;
    private String password;

    @Override
    public String getModule() {
        return "AUTENTICACION";
    }

    @Override
    public String getAction() {
        return "LOGIN";
    }

    @Override
    public String getDetail(Object response) {
        LoginUserResponse loginUserResponse = (LoginUserResponse) response;
        return "El usuario: " + email + " inicio sesion en el sistema.";
    }
}
