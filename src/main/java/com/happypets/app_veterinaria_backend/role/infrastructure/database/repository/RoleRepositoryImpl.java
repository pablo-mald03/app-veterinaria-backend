package com.happypets.app_veterinaria_backend.role.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.mapper.RoleEntityMapper;
import com.happypets.app_veterinaria_backend.user.domain.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Principal repository role implementation
 *
 */
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryPort {

    //Role repository
    private final QueryRoleRepository queryRoleRepository;

    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<Role> findByName(String name) {
        return queryRoleRepository.findByName(name).map(roleEntityMapper::toDomain);
    }

    /**
     * Pagination get all
     *
     */
    @Override
    public PaginationResult<Role> findAll(PaginationQuery paginationQuery) {

        Sort.Direction direction = "DESC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable;
        if (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank()) {
            pageable = PageRequest.of(
                    paginationQuery.getPage(),
                    paginationQuery.getSize(),
                    Sort.by(direction, paginationQuery.getSortBy())
            );
        } else {
            pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize());
        }

        Page<RoleEntity> pageResult = queryRoleRepository.findAll(pageable);

        List<Role> roles = pageResult.getContent()
                .stream()
                .map(roleEntityMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                roles,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }
}
