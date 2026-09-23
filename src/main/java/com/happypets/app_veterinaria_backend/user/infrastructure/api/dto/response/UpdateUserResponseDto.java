package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Principal update user response dto class
 *
 */
@Data
@Builder
public class UpdateUserResponseDto {
    private Long id;
    private String name;
    private String email;
}