package com.happypets.app_veterinaria_backend.user.application.command.register;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

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
    private String userRegistry;
    private String phone;
    private String rawPassword;
    private String email;
    private Set<String> roleAliases;
}
