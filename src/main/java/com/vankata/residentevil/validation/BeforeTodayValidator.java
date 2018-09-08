package com.vankata.residentevil.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BeforeTodayValidator implements ConstraintValidator<BeforeToday, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }

        return localDate.isBefore(LocalDate.now());
    }
}
