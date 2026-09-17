package com.happypets.app_veterinaria_backend.role.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.permissions.infrastructure.PermissionEntity;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.role.domain.exeptions.RoleNotFoundException;
import com.happypets.app_veterinaria_backend.role.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.mapper.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Optional<Role> findByAlias(String alias) {
        return queryRoleRepository.findByAlias(alias).map(roleEntityMapper::toDomain);
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

    /**
     * Principal method to save another role
     *
     */
    @Override
    public Role save(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setAlias(role.getAlias());
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());

        Set<PermissionEntity> permissionEntities = role.getPermissions().stream()
                .map(p -> {
                    PermissionEntity pe = new PermissionEntity();
                    pe.setId(p.getId());
                    return pe;
                })
                .collect(Collectors.toSet());

        entity.setAssignedPermissions(permissionEntities);

        RoleEntity saved = queryRoleRepository.save(entity);

        return roleEntityMapper.toDomain(saved);
    }

    /**
     * Method to update an existing role
     *
     */
    @Override
    public Role patch(Role role) {
        RoleEntity entity = queryRoleRepository.findById(role.getId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + role.getId()));

        entity.setAlias(role.getAlias());
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());

        RoleEntity updated = queryRoleRepository.save(entity);
        return roleEntityMapper.toDomain(updated);
    }

    /**
     * Method to update an existing role
     *
     */
    @Override
    public Role update(Role role) {
        RoleEntity entity = queryRoleRepository.findById(role.getId())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + role.getId()));

        entity.setName(role.getName());
        entity.setDescription(role.getDescription());
        entity.setActive(role.isActive());

        Set<PermissionEntity> permissionEntities = role.getPermissions().stream()
                .map(p -> {
                    PermissionEntity pe = new PermissionEntity();
                    pe.setId(p.getId());
                    return pe;
                })
                .collect(Collectors.toSet());
        entity.setAssignedPermissions(permissionEntities);

        RoleEntity updated = queryRoleRepository.save(entity);
        return roleEntityMapper.toDomain(updated);
    }

    /**
     * Principal method to verify if the role exists by alias
     *
     */
    @Override
    public boolean existsByAlias(String alias) {
        return queryRoleRepository.existsByAlias(alias);
    }


    /**
     * Principal method to find by aliases
     *
     */
    @Override
    public Set<Role> findByAliases(Set<String> aliases) {
        return queryRoleRepository.findByAliasInAndActiveTrue(aliases).stream()
                .map(roleEntityMapper::toDomain)
                .collect(Collectors.toSet());
    }

    /**
     * Principal method to find a role by id
     *
     */
    @Override
    public Optional<Role> findById(Long id) {
        return queryRoleRepository.findById(id).map(roleEntityMapper::toDomain);
    }


}
