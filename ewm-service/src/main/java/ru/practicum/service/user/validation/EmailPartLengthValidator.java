package ru.practicum.service.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailPartLengthValidator implements ConstraintValidator<EmailPartsLengthValidation, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isBlank()) return false;

        String[] emailParts = email.split("@");
        String localPart = emailParts[0];
        String domenPart = emailParts[1];

        return localPart.length() <= 64 || domenPart.length() <= 63;
    }
}