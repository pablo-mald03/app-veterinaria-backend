package com.happypets.app_veterinaria_backend.user.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal update user request class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest implements Request<UpdateUserResponse>, AuditableRequest {
    private Long userId;
    private String identification;
    private String name;
    private String firstName;
    private String phone;
    private String userRegistry;
    private String email;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "EDITAR USUARIO";
    }

    @Override
    public String getDetail(Object response) {
        UpdateUserResponse result = (UpdateUserResponse) response;
        return "Se modifico la informacion del usuario: " + result.getId();
    }
}
