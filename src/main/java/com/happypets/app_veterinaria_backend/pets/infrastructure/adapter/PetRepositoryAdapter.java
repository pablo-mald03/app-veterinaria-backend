package com.happypets.app_veterinaria_backend.pets.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.common.domain.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import com.happypets.app_veterinaria_backend.pets.infrastructure.database.PetEntityMapper;
import com.happypets.app_veterinaria_backend.pets.infrastructure.mapper.PetMapper;
import com.happypets.app_veterinaria_backend.pets.infrastructure.persitence.PetEntity;
import com.happypets.app_veterinaria_backend.pets.infrastructure.persitence.PetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class PetRepositoryAdapter implements PetRepository {

    private final PetJpaRepository petJpaRepository;
    private final PetEntityMapper petMapper;

    @Override
    public Pet save(Pet pet) {
        PetEntity petEntity = petMapper.toEntity(pet);
        return petMapper.toDomain(petJpaRepository.save(petEntity));
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petJpaRepository.findById(id).map(petMapper::toDomain);
    }

    @Override
    public Pet update(Pet pet) {
        PetEntity entity = petMapper.toEntity(pet);
        return petMapper.toDomain(petJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        petJpaRepository.deleteById(id);
    }

    @Override
    public PaginationResult<Pet> findAll(PaginationQuery paginationQuery) {

        Sort.Direction direction =
                paginationQuery.getDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sort = Sort.by(
                direction,
                paginationQuery.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginationQuery.getPage(),
                paginationQuery.getSize(),
                sort
        );

        Page<PetEntity> petPage = petJpaRepository.findAll(pageable);

        List<Pet> pets = petPage.getContent()
                .stream()
                .map(petMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                pets,
                petPage.getNumber(),
                petPage.getSize(),
                petPage.getTotalPages(),
                petPage.getTotalElements()
        );

    }
}
