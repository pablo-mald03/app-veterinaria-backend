package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentApplicationItemJpaRepository extends JpaRepository<TreatmentApplicationItemEntity, Long> {

    List<TreatmentApplicationItemEntity> findByIdApplication(Long idApplication);
}