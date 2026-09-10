package com.happypets.app_veterinaria_backend.user.domain.port;

import com.happypets.app_veterinaria_backend.user.domain.entity.Role;

import java.util.Optional;

/**
 * Principal role port
 *
 */
public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);
}
