package com.happypets.app_veterinaria_backend.treatment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {

    private Long idTreatment;
    private String name;
    private String description;
    private int intervalDays;
    private boolean status;

    @Builder.Default
    private Set<String> species = new HashSet<>();

    @Builder.Default
    private List<TreatmentProduct> products = new ArrayList<>();

    public boolean isRecurring() {
        return intervalDays > 0;
    }

    public boolean appliesTo(String petSpecies) {
        return petSpecies != null && species.contains(normalizeSpecies(petSpecies));
    }

    public static String normalizeSpecies(String species) {
        return species.trim().toUpperCase(Locale.ROOT);
    }
}