package com.happypets.app_veterinaria_backend.treatment.domain.port;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepositoryPort {

    Treatment save(Treatment treatment);

    Optional<Treatment> findById(Long id);

    List<Treatment> findAll();

    boolean existsByNameIgnoreCase(String name);

    void deactivate(Long id);
}
