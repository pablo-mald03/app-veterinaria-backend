package com.happypets.app_veterinaria_backend.auth.application.query;

import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Auth verify Response class
 *
 */
@Data
@AllArgsConstructor
public class AuthVerifyUserResponse {

    private AuthUser authUser;
}
