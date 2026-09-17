package com.happypets.app_veterinaria_backend.role.application.query.rolePermissions;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get permissions by roles request class
 *
 */
@Data
@AllArgsConstructor
public class GetRolePermissionsRequest implements Request<GetRolePermissionsResponse> {
    private Long id;
}
