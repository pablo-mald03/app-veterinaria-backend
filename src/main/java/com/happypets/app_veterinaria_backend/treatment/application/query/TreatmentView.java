package com.happypets.app_veterinaria_backend.treatment.application.query;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record TreatmentView(
        Long idTreatment,
        String name,
        String description,
        int intervalDays,
        boolean status,
        Set<String> species,
        List<TreatmentProduct> products,
        BigDecimal estimatedCost
) {}