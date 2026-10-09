package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.database.VaccinationCardEntityMapper;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.persistence.VaccinationCardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VaccinationCardRepositoryAdapter implements VaccinationCardRepositoryPort {

    private final VaccinationCardJpaRepository jpaRepository;
    private final VaccinationCardEntityMapper entityMapper;

    @Override
    public VaccinationCard save(VaccinationCard card) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(card)));
    }

    @Override
    public Optional<VaccinationCard> findById(Long idCard) {
        return jpaRepository.findById(idCard).map(entityMapper::toDomain);
    }

    @Override
    public Optional<VaccinationCard> findByIdPet(Long idPet) {
        return jpaRepository.findByIdPet(idPet).map(entityMapper::toDomain);
    }
}