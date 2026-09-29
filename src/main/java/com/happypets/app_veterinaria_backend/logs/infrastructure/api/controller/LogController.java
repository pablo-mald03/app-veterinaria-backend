package com.happypets.app_veterinaria_backend.logs.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.logs.application.getAll.GetAllLogsRequest;
import com.happypets.app_veterinaria_backend.logs.application.getAll.GetAllLogsResponse;
import com.happypets.app_veterinaria_backend.logs.application.logModules.GetLogModulesRequest;
import com.happypets.app_veterinaria_backend.logs.application.logModules.GetLogModulesResponse;
import com.happypets.app_veterinaria_backend.logs.domain.api.LogRestController;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.request.GetAllLogsQueryDto;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response.GetAllLogsResponseDto;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.mapper.LogMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for logs
 *
 */
@RestController
@RequestMapping("/logs")
@Tag(name = "Logs", description = "Module to manage the application logs")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class LogController implements LogRestController {

    //Mediator
    private final Mediator mediator;

    private final LogMapper logMapper;

    /**
     * Get all modules of logs endpoint
     *
     */
    @Operation(summary = "Get log modules", description = "Lista all distinct modules registerd at logs")
    @GetMapping("/modules")
    @Override
    public ResponseEntity<List<String>> getModules() {
        GetLogModulesResponse response = mediator.dispatch(new GetLogModulesRequest());
        return ResponseEntity.ok(response.getModules());
    }

    /**
     * Get all logs with pageable params and filters endpoint
     *
     */
    @PreAuthorize("hasAuthority('logs:ver')")
    @Operation(summary = "Get all logs", description = "Get all pageable logs,with filters by module and dates")
    @GetMapping
    @Override
    public ResponseEntity<GetAllLogsResponseDto> getAll(
            @ParameterObject PaginationQuery paginationQuery,
            @ParameterObject GetAllLogsQueryDto queryDto) {

        GetAllLogsRequest request = logMapper.toGetAllLogsRequest(queryDto, paginationQuery);
        GetAllLogsResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(logMapper.toGetAllLogsResponseDto(response));
    }
}
