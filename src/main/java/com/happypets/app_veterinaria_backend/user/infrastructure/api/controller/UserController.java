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
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.GetAllUsersResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.RegisterUserRequestDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.RegisterUserResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.UserResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserRequestDto registerUserRequestDto) {

        RegisterUserRequest request = userMapper.mapToRegisterUserRequest(registerUserRequestDto);
        RegisterUserResponse response = mediator.dispatch(request);

        RegisterUserResponseDto registerUserResponseDto = userMapper.mapToRegisterUserResponseDto(response);
        return ResponseEntity.ok(registerUserResponseDto);
    }

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
    @PostMapping("/recover-password")
    @Override
    public ResponseEntity<Void> recoverPassword(RecoverPasswordRequestDto requestDto) {
        RecoverPasswordRequest request = userMapper.mapToRecoverPasswordRequest(requestDto);
        mediator.dispatch(request);
        return ResponseEntity.ok().build();
    }
}
