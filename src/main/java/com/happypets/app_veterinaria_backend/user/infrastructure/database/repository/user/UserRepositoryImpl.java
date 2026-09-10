package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository.user;

import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper.UserEntityMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Principal implementation for the user repository
 *
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryPort {

    //Dependency attributes
    private final QueryUserRepository queryUserRepository;
    private final UserEntityMapper userEntityMapper;
    private final EntityManager entityManager;

    /**
     * Method to find by email (JWT provider)
     *
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return queryUserRepository.findByEmail(email).map(userEntityMapper::mapToUser);
    }

    /**
     * Method verify if de user exist by email
     *
     */
    @Override
    public boolean existByEmail(String email) {
        return queryUserRepository.findByEmail(email).isPresent();
    }

    /**
     * Method insert a user
     *
     */
    @Override
    public User insert(User user) {
        UserEntity entity = userEntityMapper.mapToUserEntity(user);

        Set<RoleEntity> roleRefs = user.getRoles().stream()
                .map(role -> entityManager.getReference(RoleEntity.class, role.getId()))
                .collect(Collectors.toSet());

        entity.setRoles(roleRefs);

        UserEntity saved = queryUserRepository.save(entity);
        return userEntityMapper.mapToUser(saved);
    }

    /**
     * Method update a user
     *
     */
    @Override
    public User update(User user) {

        UserEntity userEntity = userEntityMapper.mapToUserEntity(user);
        UserEntity saved = queryUserRepository.save(userEntity);
        return userEntityMapper.mapToUser(saved);
    }
}
