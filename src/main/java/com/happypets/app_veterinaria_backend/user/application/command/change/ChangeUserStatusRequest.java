package com.happypets.app_veterinaria_backend.user.application.command.change;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Disable user class request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStatusRequest implements Request<ChangeUserStatusResponse>, AuditableRequest {
    private Long userId;
    private boolean status;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {

        return status ? "ACTIVACION DE USUARIO" : "DESACTIVACION DE USUARIO";
    }

    @Override
    public String getDetail(Object response) {
        return (status ? "Se activo el usuario " : "Se desactivo el usuario ") + userId;
    }
}