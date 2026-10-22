package com.happypets.app_veterinaria_backend.common.infrastructure.annotations;

import com.happypets.app_veterinaria_backend.common.infrastructure.validators.NotBeforeNowValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Decorator to validate the date time
 *
 */
@Documented
@Constraint(validatedBy = NotBeforeNowValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBeforeNow {
    String message() default "La fecha no puede ser anterior a la fecha y hora actual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}