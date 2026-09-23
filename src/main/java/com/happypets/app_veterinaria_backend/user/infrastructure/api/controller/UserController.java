package com.happypets.app_veterinaria_backend.user.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusRequest;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusResponse;
import com.happypets.app_veterinaria_backend.user.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getAll.GetAllUsersRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getAll.GetAllUsersResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getById.GetUserByIdRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getById.GetUserByIdResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getRoles.GetUserRolesRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getRoles.GetUserRolesResponse;
import com.happypets.app_veterinaria_backend.user.domain.api.UserRestController;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    @SecurityRequirement(name = "cookieAuth")
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto) {

        RegisterUserRequest request = userMapper.mapToRegisterUserRequest(registerUserRequestDto);
        RegisterUserResponse response = mediator.dispatch(request);

        RegisterUserResponseDto registerUserResponseDto = userMapper.mapToRegisterUserResponseDto(response);
        return ResponseEntity.ok(registerUserResponseDto);
    }


    @Operation(summary = "Get all users", description = "Endpoint to Get all users with pagination")
    @SecurityRequirement(name = "cookieAuth")
    @GetMapping
    public ResponseEntity<GetAllUsersResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery) {
        GetAllUsersRequest getAllUsersRequest = new GetAllUsersRequest(paginationQuery);
        GetAllUsersResponse response = mediator.dispatch(getAllUsersRequest);
        return ResponseEntity.ok(userMapper.toGetAllUsersResponseDto(response));
    }

    /**
     * Recover password endpoint request
     *
     */
    @Operation(summary = "Password Recovery", description = "Endpoint to recover the user password")
    @SecurityRequirement(name = "cookieAuth")
    @PostMapping("/recover-password")
    @Override
    public ResponseEntity<Void> recoverPassword(@RequestBody @Valid RecoverPasswordRequestDto requestDto) {
        RecoverPasswordRequest request = userMapper.mapToRecoverPasswordRequest(requestDto);
        mediator.dispatch(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Find user by id endpoint request
     *
     */
    @Operation(summary = "Get user by id", description = "Get complete detail of any user")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponseDto> getById(@PathVariable Long id) {
        GetUserByIdRequest request = GetUserByIdRequest.builder().userId(id).build();
        GetUserByIdResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(userMapper.toUserDetailResponseDto(response));
    }

    /**
     * Recover password endpoint request
     *
     */
    @Operation(summary = "Update user", description = "Update the user information")
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequestDto dto) {

        UpdateUserRequest request = userMapper.toUpdateUserRequest(id, dto);
        UpdateUserResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(userMapper.toUpdateUserResponseDto(response));
    }

    /**
     * Assignation roles endpoint
     *
     */
    @Operation(summary = "Assign roles to user", description = "Modify the user permissions")
    @PutMapping("/{id}/roles")
    public ResponseEntity<AssignRolesToUserResponseDto> assignRoles(@PathVariable Long id, @RequestBody @Valid AssignRolesToUserRequestDto dto) {

        AssignRolesToUserRequest request = userMapper.toAssignRolesRequest(id, dto);
        AssignRolesToUserResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(userMapper.toAssignRolesResponseDto(response));
    }

    /**
     * Get Roles by user id endpoint
     *
     */
    @Operation(summary = "Get user roles with permissions", description = "Payload of role and permissions")
    @GetMapping("/{id}/roles")
    public ResponseEntity<UserRolesResponseDto> getUserRoles(@PathVariable Long id) {
        GetUserRolesRequest request = new GetUserRolesRequest(id);
        GetUserRolesResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(userMapper.toUserRolesResponseDto(response));
    }

    /**
     * Disable user endpoint
     *
     */
    @Operation(summary = "Change user status", description = "Activate or deactivate users")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ChangeStatusUserResponseDto> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusUserRequestDto dto) {
        ChangeUserStatusRequest request = userMapper.toChangeUserStatusRequest(id, dto);
        ChangeUserStatusResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(userMapper.toChangeStatusUserResponseDto(response));
    }
}
