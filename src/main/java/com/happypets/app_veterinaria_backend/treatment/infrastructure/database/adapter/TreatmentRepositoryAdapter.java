package com.happypets.app_veterinaria_backend.treatment.infrastructure.database.adapter;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.database.entity.TreatmentEntity;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.database.entity.TreatmentProductEntity;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.database.repository.TreatmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TreatmentRepositoryAdapter implements TreatmentRepositoryPort {

    private final TreatmentJpaRepository treatmentJpaRepository;

    @Override
    @Transactional
    public Treatment save(Treatment treatment) {
        TreatmentEntity entity = new TreatmentEntity();
        entity.setName(treatment.getName());
        entity.setDescription(treatment.getDescription());
        entity.setIntervalDays(treatment.getIntervalDays());
        entity.setStatus(treatment.isStatus());
        entity.getSpecies().addAll(treatment.getSpecies());

        treatment.getProducts().forEach(p -> entity.getProducts().add(
                TreatmentProductEntity.builder()
                        .treatment(entity)
                        .productId(p.getProductId())
                        .quantity(p.getQuantity())
                        .build()));

        return toDomain(treatmentJpaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Treatment> findById(Long id) {
        return treatmentJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findAll() {
        return treatmentJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return treatmentJpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        treatmentJpaRepository.findById(id).ifPresent(entity -> {
            entity.setStatus(false);
            treatmentJpaRepository.save(entity);
        });
    }

    private Treatment toDomain(TreatmentEntity e) {
        return Treatment.builder()
                .idTreatment(e.getIdTreatment())
                .name(e.getName())
                .description(e.getDescription())
                .intervalDays(e.getIntervalDays())
                .status(e.isStatus())
                .species(new HashSet<>(e.getSpecies()))
                .products(e.getProducts().stream()
                        .map(p -> new TreatmentProduct(p.getProductId(), p.getQuantity()))
                        .toList())
                .build();
    }
}