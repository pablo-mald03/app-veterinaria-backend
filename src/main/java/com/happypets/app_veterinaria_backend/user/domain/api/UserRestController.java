package com.happypets.app_veterinaria_backend.user.domain.api;

import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.RecoverPasswordRequestDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.RegisterUserRequestDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.RegisterUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Principal contest to the controller to manage the users
 *
 */
public interface UserRestController {

    /**
     * Method to execute the recover password action
     *
     */
    ResponseEntity<Void> recoverPassword(@RequestBody RecoverPasswordRequestDto requestDto);

    /**
     * Method to register a new user
     *
     */
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserRequestDto registerUserRequestDto);
}
