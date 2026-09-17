package com.happypets.app_veterinaria_backend.user.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;

import java.util.Optional;

/**
 * Interface to define the contest of repository with users
 *
 */
public interface UserRepositoryPort {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    PaginationResult<User> findAll(PaginationQuery paginationQuery);

    Optional<User> findByEmailAndIdentification(String email, String identification);

    boolean existsByEmail(String email);

    boolean existsByIdentification(String identification);

    boolean existsByUserRegistry(String userRegistry);

    User save(User user);

    User update(User user);

    void recoverPassword(User user);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUserRegistryAndIdNot(String userRegistry, Long id);

    boolean existsByIdentificationAndIdNot(String identification, Long id);

    long countActiveUsersByRoleAlias(String alias);
}
