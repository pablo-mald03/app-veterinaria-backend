package com.happypets.app_veterinaria_backend.report.domain.model;

import java.math.BigDecimal;

public record TreatmentFrequency(
        String treatment,
        Long timesApplied,
        BigDecimal totalCost
) {}
