package com.happypets.app_veterinaria_backend.logs.application.getAll;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.logs.domain.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get all log modules response class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllLogsResponse {
    private PaginationResult<Log> logs;
}