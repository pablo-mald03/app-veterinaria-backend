package com.happypets.app_veterinaria_backend.logs.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.logs.domain.entity.Log;
import com.happypets.app_veterinaria_backend.logs.domain.filter.LogFilter;

import java.util.List;

/**
 * Principal contest layer for the log repository
 *
 */
public interface LogRepositoryPort {

    void registerLog(Long userId, String module, String action, String detail);

    List<String> findDistinctModules();

    PaginationResult<Log> findAll(LogFilter filter, PaginationQuery pagination);
}
