package com.happypets.app_veterinaria_backend.role.application.query.findById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get by id role request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRoleByIdRequest implements Request<GetRoleByIdResponse> {
    private Long roleId;
}
