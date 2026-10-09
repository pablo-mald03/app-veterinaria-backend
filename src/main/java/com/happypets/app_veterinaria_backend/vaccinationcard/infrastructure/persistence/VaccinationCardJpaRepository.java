package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccinationCardJpaRepository extends JpaRepository<VaccinationCardEntity, Long> {

    Optional<VaccinationCardEntity> findByIdPet(Long idPet);
}
