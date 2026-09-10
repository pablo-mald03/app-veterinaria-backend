package com.happypets.app_veterinaria_backend.auth.infrastructure.api.mapper;

import com.happypets.app_veterinaria_backend.auth.application.command.login.LoginUserRequest;
import com.happypets.app_veterinaria_backend.auth.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.auth.domain.entity.AuthUser;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.AuthUserDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.LoginRequestDto;
import com.happypets.app_veterinaria_backend.auth.infrastructure.api.dto.RecoverPasswordRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Principal mapper class for the auth layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthMapper {

    /**
     * Method to transform the login dto to request
     *
     */
    LoginUserRequest mapToLoginRequest(LoginRequestDto token);

    /**
     * Method to transform the auth user domain to dto
     *
     */
    AuthUserDto mapToAuthUserDto(AuthUser authUser);

    /**
     * Method to transform the recover password dto request user request
     *
     */
    RecoverPasswordRequest mapToRecoverPasswordRequest(RecoverPasswordRequestDto recoverPasswordRequestDto);
}
