package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response;

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
public class GetAllUsersResponseDto {
    private List<UserResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}
