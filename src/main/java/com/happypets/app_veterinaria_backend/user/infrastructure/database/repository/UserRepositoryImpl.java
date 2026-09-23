package com.happypets.app_veterinaria_backend.user.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.role.infrastructure.database.entity.RoleEntity;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import com.happypets.app_veterinaria_backend.user.domain.exceptions.UserNotFoundException;
import com.happypets.app_veterinaria_backend.user.domain.port.UserRepositoryPort;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.mapper.UserEntityMapper;
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
 * Principal implementation for the user repository
 *
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryPort {

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
     * Method to find by id
     *
     */
    @Override
    public Optional<User> findById(Long id) {
        return queryUserRepository.findById(id).map(userEntityMapper::mapToUser);
    }

    /**
     * Principal method to find all the users with pagination
     *
     */
    @Override
    public PaginationResult<User> findAll(PaginationQuery paginationQuery) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(paginationQuery.getDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable;
        if (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank()) {
            pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), Sort.by(direction, paginationQuery.getSortBy()));
        } else {
            pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize());
        }

        Page<UserEntity> pageResult = queryUserRepository.findAll(pageable);

        List<User> roles = pageResult.getContent().stream().map(userEntityMapper::mapToUser).toList();

        return new PaginationResult<>(roles, pageResult.getNumber(), pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements());
    }

    /**
     * Method to find by email and identification
     *
     */
    @Override
    public Optional<User> findByEmailAndIdentification(String email, String identification) {
        return queryUserRepository.findByEmailAndIdentification(email, identification).map(userEntityMapper::mapToUser);
    }

    /**
     * Method verify if de user exist by email
     *
     */
    @Override
    public boolean existsByEmail(String email) {
        return queryUserRepository.existsByEmail(email);
    }

    /**
     * Method verify if de user exist by identification
     *
     */
    @Override
    public boolean existsByIdentification(String identification) {
        return queryUserRepository.existsByIdentification(identification);
    }


    /**
     * Method verify if de user exist by username (user registry)
     *
     */
    @Override
    public boolean existsByUserRegistry(String userRegistry) {
        return queryUserRepository.existsByUserRegistry(userRegistry);
    }


    /**
     * Method insert a user
     *
     */
    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setIdentification(user.getIdentification());
        entity.setName(user.getName());
        entity.setPhone(user.getPhone());
        entity.setFirstName(user.getFirstName());
        entity.setUserRegistry(user.getUserRegistry());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        entity.setStatus(user.isStatus());

        Set<RoleEntity> roleEntities = user.getRoles().stream().map(r -> {
            RoleEntity re = new RoleEntity();
            re.setId(r.getId());
            return re;
        }).collect(Collectors.toSet());
        entity.setRoles(roleEntities);

        UserEntity saved = queryUserRepository.save(entity);
        return userEntityMapper.mapToUser(saved);
    }

    /**
     * Method update a user
     *
     */
    @Override
    public User update(User user) {
        UserEntity entity = queryUserRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + user.getId()));

        entity.setName(user.getName());
        entity.setFirstName(user.getFirstName());
        entity.setPhone(user.getPhone());
        entity.setEmail(user.getEmail());
        entity.setIdentification(user.getIdentification());
        entity.setUserRegistry(user.getUserRegistry());
        entity.setStatus(user.isStatus());

        if (user.getRoles() != null) {
            Set<RoleEntity> roleEntities = user.getRoles().stream()
                    .map(r -> {
                        RoleEntity re = new RoleEntity();
                        re.setId(r.getId());
                        return re;
                    })
                    .collect(Collectors.toSet());
            entity.setRoles(roleEntities);
        }

        UserEntity updated = queryUserRepository.save(entity);
        return userEntityMapper.mapToUser(updated);
    }

    /**
     * Method to recover the password
     *
     */
    @Override
    public void recoverPassword(User user) {
        UserEntity entity = queryUserRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + user.getId()));

        entity.setPassword(user.getPassword());

        queryUserRepository.save(entity);
    }

    /**
     * Method to verify if the user exists by email (Himself excluded)
     *
     */
    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return queryUserRepository.existsByEmailAndIdNot(email, id);
    }

    /**
     * Method to verify if the user exists by username (Himself excluded)
     *
     */
    @Override
    public boolean existsByUserRegistryAndIdNot(String userRegistry, Long id) {
        return queryUserRepository.existsByUserRegistryAndIdNot(userRegistry, id);
    }

    /**
     * Method to verify if the user exists by identification (Himself excluded)
     *
     */
    @Override
    public boolean existsByIdentificationAndIdNot(String identification, Long id) {
        return queryUserRepository.existsByIdentificationAndIdNot(identification, id);
    }

    /**
     * Method to verify if the database has one active admin user
     *
     */
    @Override
    public long countActiveUsersByRoleAlias(String alias) {
        return queryUserRepository.countActiveUsersByRoleAlias(alias);
    }
}
