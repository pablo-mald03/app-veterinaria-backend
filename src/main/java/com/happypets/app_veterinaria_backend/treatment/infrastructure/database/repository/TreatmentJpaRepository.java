package com.happypets.app_veterinaria_backend.treatment.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.treatment.infrastructure.database.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentJpaRepository extends JpaRepository<TreatmentEntity, Long> {

    boolean existsByNameIgnoreCase(String name);
}