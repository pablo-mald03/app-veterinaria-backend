package com.happypets.app_veterinaria_backend.logs.infrastructure.database.specification;

import com.happypets.app_veterinaria_backend.logs.infrastructure.database.entity.LogEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;


/**
 * Principal filter class specifications for the logs persistence layer
 *
 */
public class LogSpecifications {

    private static final ZoneId APP_ZONE = ZoneId.of("America/Guatemala");

    /**
     * Module filter
     *
     */
    public static Specification<LogEntity> hasModule(String module) {
        return (root, query, cb) -> (module == null || module.isBlank()) ? null : cb.equal(root.get("module"), module);
    }

    /**
     * Created from filter
     *
     */
    public static Specification<LogEntity> createdFrom(LocalDate from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), from.atStartOfDay(APP_ZONE).toInstant());
    }

    /**
     * Created to filter
     *
     */
    public static Specification<LogEntity> createdTo(LocalDate to) {
        return (root, query, cb) -> to == null ? null : cb.lessThan(root.get("createdAt"), to.plusDays(1).atStartOfDay(APP_ZONE).toInstant());
    }

    /**
     * Created fetch user data filter with lazy
     *
     */
    public static Specification<LogEntity> fetchUser() {
        return (root, query, cb) -> {

            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("user", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }
}
