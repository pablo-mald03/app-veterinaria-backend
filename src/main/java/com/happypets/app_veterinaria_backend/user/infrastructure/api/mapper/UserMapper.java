package com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.RecoverPasswordRequestDto;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.dto.RoleSummaryDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.mapper.RoleEntityMapper;
import com.happypets.app_veterinaria_backend.user.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserResponse;
import com.happypets.app_veterinaria_backend.user.application.query.GetAllUsersResponse;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.RegisterUserRequestDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.GetAllUsersResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.RegisterUserResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.UserResponseDto;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper.PermissionEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Principal mapper class for the user layers
 *
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleEntityMapper.class}
)
public interface UserMapper {

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


    UserResponseDto toUserResponseDto(User user);

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

}
