package com.happypets.app_veterinaria_backend.auth.application.command.login;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal login response class
 *
 */
@Data
@AllArgsConstructor
public class LoginUserResponse {
    private String token;
}
