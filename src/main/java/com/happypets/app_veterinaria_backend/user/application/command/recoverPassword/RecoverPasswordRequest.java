package com.happypets.app_veterinaria_backend.user.application.command.recoverPassword;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal recover password request class
 *
 */
@Data
@AllArgsConstructor
public class RecoverPasswordRequest implements Request<Void>, AuditableRequest {

    private String dpi;
    private String email;
    private String password;
    private String confirmationPassword;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "CAMBIO DE CONTRASEÑA";
    }

    @Override
    public String getDetail(Object response) {
        return "El usuario con identificacion '" + dpi + "' reestablecio su contraseña";
    }
}
