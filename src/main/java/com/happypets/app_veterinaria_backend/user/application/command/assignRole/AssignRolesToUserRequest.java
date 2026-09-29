package com.happypets.app_veterinaria_backend.user.application.command.assignRole;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.role.application.query.rolePermissions.GetRolePermissionsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * Principal update user roles request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToUserRequest implements Request<AssignRolesToUserResponse>, AuditableRequest {
    private Long userId;
    private Set<String> roleAliases;

    @Override
    public String getModule() {
        return "USUARIOS";
    }

    @Override
    public String getAction() {
        return "ASIGNACION DE ROLES";
    }

    @Override
    public String getDetail(Object response) {
        AssignRolesToUserResponse result = (AssignRolesToUserResponse) response;
        return "Se actualizaron los roles del usuario " + result.getUserId() + " a: " + result.getRoles().size();
    }
}