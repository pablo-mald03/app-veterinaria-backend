package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepository;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Principal implementation for the user repository
 *
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    //Dependency attributes
    private final QueryUserRepository queryUserRepository;
    private final UserEntityMapper userEntityMapper;

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
        UserEntity userEntity = userEntityMapper.mapToUserEntity(user);
        UserEntity saved = queryUserRepository.save(userEntity);
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
