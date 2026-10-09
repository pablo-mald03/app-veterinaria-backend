package com.happypets.app_veterinaria_backend.room.infrastructure.database.specification;

import com.happypets.app_veterinaria_backend.room.domain.filter.RoomFilter;
import com.happypets.app_veterinaria_backend.room.infrastructure.database.entity.RoomEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class RoomSpecification {

    private static final char ESCAPE = '\\';

    private RoomSpecification() {
    }

    public static Specification<RoomEntity> withCriteria(RoomFilter criteria) {
        return Specification.allOf(
                contains("name", criteria.getName()),
                contains("location", criteria.getLocation()),
                equalTo("number", criteria.getNumber()),
                equalTo("status", criteria.getStatus()));
    }

    /**
     * Method to filter by LIKE %valor% sin distinguir mayúsculas; si viene vacío, no filtra.
     */
    private static Specification<RoomEntity> contains(String field, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return null;
            }
            return cb.like(cb.lower(root.<String>get(field)), likePattern(value), ESCAPE);
        };
    }

    /**
     * Method to find the exactly matching for an existing room
     */
    private static Specification<RoomEntity> equalTo(String field, Object value) {
        return (root, query, cb) -> value == null ? null : cb.equal(root.get(field), value);
    }

    /**
     * Sanitization helper Method
     *
     */
    private static String likePattern(String value) {
        String escaped = value.trim().toLowerCase(Locale.ROOT)
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped + "%";
    }
}