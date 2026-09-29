package com.happypets.app_veterinaria_backend.logs.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.logs.domain.entity.Log;
import com.happypets.app_veterinaria_backend.logs.domain.filter.LogFilter;
import com.happypets.app_veterinaria_backend.logs.domain.port.LogRepositoryPort;
import com.happypets.app_veterinaria_backend.logs.infrastructure.database.entity.LogEntity;
import com.happypets.app_veterinaria_backend.logs.infrastructure.database.mapper.LogEntityMapper;
import com.happypets.app_veterinaria_backend.logs.infrastructure.database.specification.LogSpecifications;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.repository.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Log persistency layer adapter
 *
 */
@Repository
@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepositoryPort {

    private final QueryUserRepository queryUserRepository;
    private final QueryLogRepository queryLogRepository;

    private final LogEntityMapper logEntityMapper;

    /**
     * Principal method to register a new log
     *
     */
    @Async
    @Transactional
    @Override
    public void registerLog(Long userId, String module, String action, String detail) {

        UserEntity userProxy = queryUserRepository.getReferenceById(userId);

        LogEntity log = new LogEntity();
        log.setModule(module);
        log.setAction(action);
        log.setDetail(detail);
        log.setUser(userProxy);

        queryLogRepository.save(log);
    }

    /**
     * Principal method to get the different logged modules
     *
     */
    @Override
    public List<String> findDistinctModules() {
        return queryLogRepository.findDistinctModules();
    }

    /**
     * Principal method to find all logs with pagination and filters
     *
     */
    @Override
    public PaginationResult<Log> findAll(LogFilter filter, PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "createdAt";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));

        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Specification<LogEntity> spec = Specification
                .where(LogSpecifications.hasModule(filter.getModule()))
                .and(LogSpecifications.createdFrom(filter.getCreatedFrom()))
                .and(LogSpecifications.createdTo(filter.getCreatedTo()))
                .and(LogSpecifications.fetchUser());

        Page<LogEntity> page = queryLogRepository.findAll(spec, pageable);

        List<Log> content = page.getContent().stream()
                .map(logEntityMapper::mapToLog)
                .toList();

        return new PaginationResult<>(content, page.getNumber(), page.getSize(),
                page.getTotalPages(), page.getTotalElements());
    }
}
