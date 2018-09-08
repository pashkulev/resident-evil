package com.vankata.residentevil.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberRangeValidator implements ConstraintValidator<NumberRange, Integer> {

    private int min;

    private int max;

    @Override
    public void initialize(NumberRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer >= this.min && integer <= this.max;
    }
}
