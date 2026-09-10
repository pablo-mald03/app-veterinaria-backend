package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.dto.RoleSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal class for users response Dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String identification;
    private String name;
    private String firstName;
    private String email;
    private String phone;
    private String userRegistry;
    private List<RoleSummaryDto> roles;
}
