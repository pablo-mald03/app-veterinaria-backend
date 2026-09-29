package com.happypets.app_veterinaria_backend.logs.domain.api;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.request.GetAllLogsQueryDto;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response.GetAllLogsResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Principal Log controller contest
 *
 */
public interface LogRestController {

    ResponseEntity<List<String>> getModules();

    ResponseEntity<GetAllLogsResponseDto> getAll(@ParameterObject PaginationQuery paginationQuery, @ParameterObject GetAllLogsQueryDto queryDto);
}
