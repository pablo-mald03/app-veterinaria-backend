package com.happypets.app_veterinaria_backend.user.application.query.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get by id user request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByIdRequest implements Request<GetUserByIdResponse> {
    private Long userId;
}
