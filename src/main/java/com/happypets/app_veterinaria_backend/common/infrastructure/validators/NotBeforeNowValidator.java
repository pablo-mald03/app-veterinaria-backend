package com.happypets.app_veterinaria_backend.common.infrastructure.validators;

import com.happypets.app_veterinaria_backend.common.infrastructure.annotations.NotBeforeNow;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Principal not before validator class
 *
 */
@Component
@RequiredArgsConstructor
public class NotBeforeNowValidator implements ConstraintValidator<NotBeforeNow, LocalDateTime> {

    private final Clock clock;

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isBefore(LocalDateTime.now(clock));
    }
}