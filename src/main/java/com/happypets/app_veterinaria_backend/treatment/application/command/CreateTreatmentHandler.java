package com.happypets.app_veterinaria_backend.treatment.application.command;

import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateTreatmentHandler {

    private final TreatmentRepositoryPort treatmentRepository;
    private final ProductRepositoryPort productPort;

    @Transactional
    public Treatment execute(CreateTreatmentCommand command) {
        validate(command);

        Treatment treatment = Treatment.builder()
                .name(command.name().trim())
                .description(command.description())
                .intervalDays(command.intervalDays())
                .status(true)
                .species(command.species().stream()
                        .map(Treatment::normalizeSpecies)
                        .collect(Collectors.toSet()))
                .products(command.products())
                .build();

        return treatmentRepository.save(treatment);
    }

    private void validate(CreateTreatmentCommand c) {
        if (c.name() == null || c.name().isBlank()) {
            throw new IllegalArgumentException("El nombre del tratamiento es obligatorio");
        }
        if (treatmentRepository.existsByNameIgnoreCase(c.name().trim())) {
            throw new IllegalArgumentException("Ya existe un tratamiento con el nombre '" + c.name().trim() + "'");
        }
        if (c.intervalDays() < 0) {
            throw new IllegalArgumentException("Los días de reaplicación no pueden ser negativos");
        }
        if (c.species() == null || c.species().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar al menos una especie");
        }
        if (c.products() == null || c.products().isEmpty()) {
            throw new IllegalArgumentException("El tratamiento debe tener al menos un producto");
        }

        Set<Long> seen = new HashSet<>();
        for (TreatmentProduct p : c.products()) {
            if (p.getProductId() == null || !productPort.existsById(p.getProductId())) {
                throw new IllegalArgumentException("El producto ID " + p.getProductId() + " no existe");
            }
            if (!seen.add(p.getProductId())) {
                throw new IllegalArgumentException("El producto ID " + p.getProductId() + " está repetido");
            }
            if (p.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad del producto ID " + p.getProductId() + " debe ser mayor que cero");
            }
        }
    }
}