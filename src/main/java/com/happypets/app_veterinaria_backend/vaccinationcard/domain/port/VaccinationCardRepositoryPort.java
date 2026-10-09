package com.happypets.app_veterinaria_backend.vaccinationcard.domain.port;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;

import java.util.Optional;

public interface VaccinationCardRepositoryPort {

    VaccinationCard save(VaccinationCard card);

    Optional<VaccinationCard> findById(Long idCard);

    Optional<VaccinationCard> findByIdPet(Long idPet);
}
