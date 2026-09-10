package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence layer for users
 *
 */
@Repository
public interface QueryUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndIdentification(String email, String identification);
}
