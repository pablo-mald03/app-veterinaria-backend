package com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Log pagination response class dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLogsResponseDto {
    private List<LogResponseDto> logs;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}