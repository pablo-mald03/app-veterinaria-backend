package com.happypets.app_veterinaria_backend.user.application.command.register;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal register user request
 *
 */
@Data
@AllArgsConstructor
public class RegisterUserRequest implements Request<RegisterUserResponse> {

    private String identification;

    private String name;

    private String firstName;

    private String email;

    private String phone;

    private String username;

    private String password;

    private String role;
}
