package com.happypets.app_veterinaria_backend.category.infrastructure.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;

@Service
public class SlugifyCategoryService {
    public String generateAlias(String name) {
        if (name == null || name.isBlank()) {
            return "";
        }

        String normalized = name.trim().toLowerCase(Locale.ROOT);

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);

        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        normalized = normalized.replaceAll("[^a-z0-9]+", "-");

        normalized = normalized.replaceAll("^-+|-+$", "");

        return normalized;
    }
}