package com.happypets.app_veterinaria_backend.user.application.query.getRoles;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get user roles request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRolesRequest implements Request<GetUserRolesResponse> {
    private Long userId;
}