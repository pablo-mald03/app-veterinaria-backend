package com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper.RoleMapper;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.mapper.RoleEntityMapper;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusRequest;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusResponse;
import com.happypets.app_veterinaria_backend.user.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getAll.GetAllUsersResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getById.GetUserByIdResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getRoles.GetUserRolesResponse;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.*;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Principal mapper class for the user layers
 *
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleEntityMapper.class, RoleMapper.class}
)
public interface UserMapper {


    /**
     * Method to transform the user details response application to user detail dto response
     *
     */
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToAliases")
    UserDetailResponseDto toUserDetailResponseDto(GetUserByIdResponse response);

    /**
     * Method to transform the user update application to user response dto
     *
     */
    UpdateUserResponseDto toUpdateUserResponseDto(UpdateUserResponse response);


    /**
     * Method to transform update user request dto to update user request
     *
     */
    @Mapping(target = "userId", source = "id")
    UpdateUserRequest toUpdateUserRequest(Long id, UpdateUserRequestDto dto);

    /**
     * Mapper helper to transform the role with alias to role domain
     *
     */
    @Named("rolesToAliases")
    default Set<String> rolesToAliases(Set<Role> roles) {
        return roles.stream().map(Role::getAlias).collect(Collectors.toSet());
    }

    /**
     * Method to transform the recover password dto request user request
     *
     */
    RecoverPasswordRequest mapToRecoverPasswordRequest(RecoverPasswordRequestDto recoverPasswordRequestDto);

    /**
     * Method to transform the recover password dto request user request
     *
     */
    RegisterUserRequest mapToRegisterUserRequest(RegisterUserRequestDto registerUserRequestDto);

    /**
     * Method to transform the register user dto request
     *
     */
    RegisterUserResponseDto mapToRegisterUserResponseDto(RegisterUserResponse registerUserResponse);


    /**
     * Method to transform user domain to user response dto
     *
     */
    UserResponseDto toUserResponseDto(User user);

    /**
     * Method to transform user domain list to user response dto list
     *
     */
    List<UserResponseDto> toUserResponseDtoList(List<User> users);

    default GetAllUsersResponseDto toGetAllUsersResponseDto(GetAllUsersResponse response) {
        PaginationResult<User> result = response.getAllUsers();
        return GetAllUsersResponseDto.builder()
                .content(toUserResponseDtoList(result.getContent()))
                .page(result.getPage())
                .size(result.getSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }

    /**
     * Method to transform the assignation roles user request dto to the request
     *
     */
    @Mapping(target = "userId", source = "id")
    AssignRolesToUserRequest toAssignRolesRequest(Long id, AssignRolesToUserRequestDto dto);


    /**
     * Method to transform the assignation roles user response to the response dto
     *
     */
    AssignRolesToUserResponseDto toAssignRolesResponseDto(AssignRolesToUserResponse response);

    /**
     * Method to transform user roles response to the user roles response dto
     *
     */
    UserRolesResponseDto toUserRolesResponseDto(GetUserRolesResponse response);

    /**
     * Method to transform disable/enable user request dto for user to the user status  response dto
     *
     */
    @Mapping(target = "userId", source = "id")
    ChangeUserStatusRequest toChangeUserStatusRequest(Long id, ChangeStatusUserRequestDto dto);

    ChangeStatusUserResponseDto toChangeStatusUserResponseDto(ChangeUserStatusResponse response);
}