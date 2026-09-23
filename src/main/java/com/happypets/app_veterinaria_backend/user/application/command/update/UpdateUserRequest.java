package com.happypets.app_veterinaria_backend.user.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
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
public class UpdateUserRequest implements Request<UpdateUserResponse> {
    private Long userId;
    private String identification;
    private String name;
    private String firstName;
    private String phone;
    private String userRegistry;
    private String email;
}
