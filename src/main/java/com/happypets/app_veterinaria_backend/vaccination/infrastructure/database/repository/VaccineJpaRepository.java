package com.happypets.app_veterinaria_backend.vaccination.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.vaccination.infrastructure.database.entity.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineJpaRepository extends JpaRepository<VaccineEntity, Long> {
}
