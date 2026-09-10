package com.happypets.app_veterinaria_backend.user.domain.port;


import com.happypets.app_veterinaria_backend.user.domain.entity.User;

import java.util.Optional;

/**
 * Interface to define the contest of repository with users
 *
 */
public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existByEmail(String email);

    User insert(User user);

    User update(User user);

}
