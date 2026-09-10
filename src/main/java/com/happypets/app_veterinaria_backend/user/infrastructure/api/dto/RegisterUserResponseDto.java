package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal register user response dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponseDto {

    private String name;
}
