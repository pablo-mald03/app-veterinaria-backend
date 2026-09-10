package com.happypets.app_veterinaria_backend.auth.application.command.login;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal login request class
 *
 */
@Data
@AllArgsConstructor
public class LoginUserRequest implements Request<LoginUserResponse> {

    private String email;
    private String password;
}
