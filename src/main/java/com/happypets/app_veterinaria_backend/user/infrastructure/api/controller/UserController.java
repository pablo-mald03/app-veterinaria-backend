package com.happypets.app_veterinaria_backend.user.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.RecoverPasswordRequestDto;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.user.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserResponse;
import com.happypets.app_veterinaria_backend.user.application.query.GetAllUsersRequest;
import com.happypets.app_veterinaria_backend.user.application.query.GetAllUsersResponse;
import com.happypets.app_veterinaria_backend.user.domain.api.UserRestController;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.RegisterUserRequestDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.GetAllUsersResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.RegisterUserResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Module to manage the users")
@RequiredArgsConstructor
public class UserController implements UserRestController {

    /*Mediator*/
    private final Mediator mediator;

    //Mapper
    private final UserMapper userMapper;


    /**
     * Create user base endpoint request
     *
     */
    @Operation(summary = "Register new user", description = "Endpoint to register a new user")
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto) {

        RegisterUserRequest request = userMapper.mapToRegisterUserRequest(registerUserRequestDto);
        RegisterUserResponse response = mediator.dispatch(request);

        RegisterUserResponseDto registerUserResponseDto = userMapper.mapToRegisterUserResponseDto(response);
        return ResponseEntity.ok(registerUserResponseDto);
    }

    @Operation(summary = "Get all users", description = "Endpoint to Get all products with pagination")
    @GetMapping
    public ResponseEntity<GetAllUsersResponseDto> getAll(PaginationQuery paginationQuery) {
        GetAllUsersRequest getAllUsersRequest = new GetAllUsersRequest(paginationQuery);
        GetAllUsersResponse response = mediator.dispatch(getAllUsersRequest);
        return ResponseEntity.ok(userMapper.toGetAllUsersResponseDto(response));
    }

    /**
     * Recover password endpoint request
     *
     */
    @Operation(summary = "Password Recovery", description = "Endpoint to recover the user password")
    @PostMapping("/recover-password")
    @Override
    public ResponseEntity<Void> recoverPassword(@RequestBody @Valid RecoverPasswordRequestDto requestDto) {
        RecoverPasswordRequest request = userMapper.mapToRecoverPasswordRequest(requestDto);
        mediator.dispatch(request);
        return ResponseEntity.ok().build();
    }
}
