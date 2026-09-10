package com.happypets.app_veterinaria_backend.user.application.command.register;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal register user response
 *
 */
@Data
@AllArgsConstructor
public class RegisterUserResponse {
    private String name;
}
