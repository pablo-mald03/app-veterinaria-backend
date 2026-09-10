package com.happypets.app_veterinaria_backend.user.domain.port;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.domain.entity.Role;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;

import java.util.Optional;

/**
 * Interface to define the contest of repository with users
 *
 */
public interface UserRepositoryPort {

    Optional<User> findByEmail(String email);

    PaginationResult<User> findAll(PaginationQuery paginationQuery);

    boolean existByEmail(String email);

    User insert(User user);

    User update(User user);
}
