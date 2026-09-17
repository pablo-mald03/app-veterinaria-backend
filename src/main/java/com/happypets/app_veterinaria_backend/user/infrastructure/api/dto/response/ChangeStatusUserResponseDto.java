package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Status user dto response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusUserResponseDto {
    private Long userId;
    private boolean status;
}