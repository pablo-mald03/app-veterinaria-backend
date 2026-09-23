package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence layer for users
 *
 */
@Repository
public interface QueryUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByIdentification(String identification);

    boolean existsByUserRegistry(String userRegistry);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByIdentificationAndIdNot(String identification, Long id);

    boolean existsByUserRegistryAndIdNot(String userRegistry, Long id);

    Optional<UserEntity> findByEmail(String email);

    @Query("""
                SELECT COUNT(DISTINCT user)
                FROM UserEntity user
                JOIN user.roles rol
                WHERE rol.alias = :alias AND rol.active = true AND user.status = true
            """)
    long countActiveUsersByRoleAlias(@Param("alias") String alias);

    Optional<UserEntity> findByEmailAndIdentification(String email, String identification);
}
