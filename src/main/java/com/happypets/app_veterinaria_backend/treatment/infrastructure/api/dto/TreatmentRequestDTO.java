package com.happypets.app_veterinaria_backend.treatment.infrastructure.api.dto;

import com.happypets.app_veterinaria_backend.treatment.application.command.CreateTreatmentCommand;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record TreatmentRequestDTO(
        @NotBlank @Size(max = 150) String name,
        @Size(max = 255) String description,
        @NotNull @Min(0) Integer intervalDays,
        @NotEmpty Set<@NotBlank String> species,
        @NotEmpty @Valid List<ProductLineDTO> products
) {
    public record ProductLineDTO(
            @NotNull Long productId,
            @NotNull @Positive Integer quantity
    ) {}

    public CreateTreatmentCommand toCommand() {
        return new CreateTreatmentCommand(
                name,
                description,
                intervalDays,
                species,
                products.stream()
                        .map(p -> new TreatmentProduct(p.productId(), p.quantity()))
                        .collect(Collectors.toList()));
    }
}
