package com.happypets.app_veterinaria_backend.auth.domain.api;

import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.AuthUserDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal authentication port controller
 *
 */
public interface AuthenticationRestController {

    ResponseEntity<Void> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response);

    ResponseEntity<AuthUserDto> getCurrentUser();

    ResponseEntity<Void> logout(HttpServletResponse response);
}
