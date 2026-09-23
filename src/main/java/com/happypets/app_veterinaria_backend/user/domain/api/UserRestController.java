package com.happypets.app_veterinaria_backend.user.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.*;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    ResponseEntity<Void> recoverPassword(@RequestBody @Valid RecoverPasswordRequestDto requestDto);

    /**
     * Method to register a new user
     *
     */
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto);

    /**
     * Method to get all users
     *
     */
    ResponseEntity<GetAllUsersResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery);

    /**
     * Method to get user by id
     *
     */
    ResponseEntity<UserDetailResponseDto> getById(@PathVariable Long id);

    /**
     * Method to update base attributes
     *
     */
    ResponseEntity<UpdateUserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequestDto dto);

    /**
     * Method to assign roles to the user
     *
     */
    ResponseEntity<AssignRolesToUserResponseDto> assignRoles(@PathVariable Long id, @RequestBody @Valid AssignRolesToUserRequestDto dto);

    /**
     * Method to get roles assigned to the user
     *
     */
    ResponseEntity<UserRolesResponseDto> getUserRoles(@PathVariable Long id);


    /**
     * Method to disable/enable user
     *
     */
    ResponseEntity<ChangeStatusUserResponseDto> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusUserRequestDto dto);
}