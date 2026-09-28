package com.happypets.app_veterinaria_backend.logs.application.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Get all log modules request class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllLogsRequest implements Request<GetAllLogsResponse> {
    private String module;
    private LocalDate createdFrom;
    private LocalDate createdTo;
    private PaginationQuery paginationQuery;
}