package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.RoleSummaryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Principal role response dto
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesResponseDto {
    private Long userId;
    private String userRegistry;
    private String identification;
    private String email;
    private List<RoleSummaryResponseDto> roles;
}
