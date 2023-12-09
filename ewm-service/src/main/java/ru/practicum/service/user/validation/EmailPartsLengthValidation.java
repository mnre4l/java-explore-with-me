package ru.practicum.service.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailPartLengthValidator.class)
public @interface EmailPartsLengthValidation {
    String message() default "Bad email part length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
