package ru.practicum.service.event.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventDateValidation implements ConstraintValidator<NoLaterThanTwoHours, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime start, ConstraintValidatorContext constraintValidatorContext) {
        if (start == null) return true;
        return start.minusHours(2).isAfter(LocalDateTime.now());
    }
}