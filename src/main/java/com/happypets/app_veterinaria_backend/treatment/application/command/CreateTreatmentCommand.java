package com.happypets.app_veterinaria_backend.treatment.application.command;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;

import java.util.List;
import java.util.Set;

public record CreateTreatmentCommand(
        String name,
        String description,
        int intervalDays,
        Set<String> species,
        List<TreatmentProduct> products
) {}