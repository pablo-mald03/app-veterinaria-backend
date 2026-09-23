package com.happypets.app_veterinaria_backend.pets.infrastructure.persitence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
}
