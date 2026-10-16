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

    private final VaccinationCardJpaRepository vaccinationCardJpaRepository;

    /*MAPPER VACCINATION*/
    private final VaccinationCardEntityMapper vaccinationCardEntityMapper;

    @Override
    public VaccinationCard save(VaccinationCard card) {
        return vaccinationCardEntityMapper.toDomain(vaccinationCardJpaRepository.save(vaccinationCardEntityMapper.toEntity(card)));
    }

    @Override
    public Optional<VaccinationCard> findById(Long idCard) {
        return vaccinationCardJpaRepository.findById(idCard).map(vaccinationCardEntityMapper::toDomain);
    }

    @Override
    public Optional<VaccinationCard> findByIdPet(Long idPet) {
        return vaccinationCardJpaRepository.findByIdPet(idPet).map(vaccinationCardEntityMapper::toDomain);
    }
}