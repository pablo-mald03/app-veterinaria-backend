package com.happypets.app_veterinaria_backend.permissions.infrastructure.database.repository;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.domain.entity.Permission;
import com.happypets.app_veterinaria_backend.permissions.domain.filter.PermissionFilter;
import com.happypets.app_veterinaria_backend.permissions.domain.port.PermissionRepositoryPort;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.entity.PermissionEntity;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.mapper.PermissionEntityMapper;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.database.specification.PermissionSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Principal implementation of permission repository
 *
 */
@Service
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepositoryPort {


    private final QueryPermissionRepository queryPermissionRepository;
    private final PermissionEntityMapper permissionEntityMapper;

    /**
     * Method to verify if the ids exists
     *
     */
    @Override
    public Set<Long> findExistingIds(Set<Long> requestedIds) {
        return queryPermissionRepository.findAllById(requestedIds).stream()
                .map(PermissionEntity::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Method to return the permissions by id
     */
    @Override
    public Set<Permission> findByIds(Set<Long> ids) {
        return queryPermissionRepository.findAllById(ids).stream()
                .map(permissionEntityMapper::toDomain)
                .collect(Collectors.toSet());
    }

    /**
     * Method to return the permissions by filters
     */
    @Override
    public PaginationResult<Permission> findAll(PermissionFilter filter, PaginationQuery pagination) {

        Pageable pageable = PageRequest.of(
                pagination.getPage(),
                pagination.getSize()
        );

        Page<PermissionEntity> page = queryPermissionRepository.findAll(
                PermissionSpecifications.byFilter(filter),
                pageable
        );

        List<Permission> content = page.getContent().stream()
                .map(permissionEntityMapper::toDomain)
                .toList();

        return new PaginationResult<>(content, page.getNumber(), page.getSize(),
                page.getTotalPages(), page.getTotalElements());
    }
}
