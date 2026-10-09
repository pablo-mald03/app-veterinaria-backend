package com.happypets.app_veterinaria_backend.vaccination.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.database.VaccineEntityMapper;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.persistence.VaccineEntity;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.persistence.VaccineJpaRepository;
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
public class VaccineRepositoryAdapter implements VaccineRepository {

    private final VaccineJpaRepository vaccineJpaRepository;
    private final VaccineEntityMapper vaccineEntityMapper;

    @Override
    public Vaccine save(Vaccine vaccine) {
        VaccineEntity vaccineEntity = vaccineEntityMapper.toEntity(vaccine);
        return vaccineEntityMapper.toDomain(vaccineJpaRepository.save(vaccineEntity));
    }

    @Override
    public Optional<Vaccine> findById(Long id) {
        return vaccineJpaRepository.findById(id).map(vaccineEntityMapper::toDomain);
    }

    @Override
    public PaginationResult<Vaccine> findAll(PaginationQuery paginationQuery) {

        Sort.Direction direction = paginationQuery.getDirection().equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, paginationQuery.getSortBy());

        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Page<VaccineEntity> vaccineEntity = vaccineJpaRepository.findAll(pageable);

        List<Vaccine> vaccines = vaccineEntity.getContent()
                .stream()
                .map(vaccineEntityMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                vaccines,
                vaccineEntity.getNumber(),
                vaccineEntity.getSize(),
                vaccineEntity.getTotalPages(),
                vaccineEntity.getTotalElements()
        );
    }

    @Override
    public void desactiveById(Long id) {
        vaccineJpaRepository.findById(id).ifPresent(vaccineEntity -> {
            vaccineEntity.setStatus(false);
            vaccineJpaRepository.save(vaccineEntity);
        });
    }
}
