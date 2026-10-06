package com.happypets.app_veterinaria_backend.user.application.query.getProfile;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get profile request class
 *
 */
@Data
@NoArgsConstructor
public class GetProfileRequest implements Request<GetProfileResponse>, AuditableRequest {


    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "BUSQUEDA DE PERFIL ";
    }

    @Override
    public String getDetail(Object response) {
        if (response instanceof GetProfileResponse profileResponse) {
            return "Se consulto informacion del usuario con ID:" + profileResponse.getUser().getId();
        }
        return "Se consulto el perfil del usuario";
    }
}