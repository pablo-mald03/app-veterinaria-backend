package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository.role;

import com.happypets.app_veterinaria_backend.user.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.domain.port.RoleRepositoryPort;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
