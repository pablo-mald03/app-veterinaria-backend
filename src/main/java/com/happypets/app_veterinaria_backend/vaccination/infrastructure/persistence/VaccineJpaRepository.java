package com.happypets.app_veterinaria_backend.vaccination.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineJpaRepository extends JpaRepository<VaccineEntity, Long> {
}
