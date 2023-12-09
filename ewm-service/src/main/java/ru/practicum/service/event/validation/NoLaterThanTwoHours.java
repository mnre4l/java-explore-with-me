package ru.practicum.service.event.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidation.class)
public @interface NoLaterThanTwoHours {
    String message() default "Старт события должен быть минимум через 2 часа";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
