package com.vankata.residentevil.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordVerificationValidator implements ConstraintValidator<PasswordVerification, Object> {

    private String password;
    private String confirmPassword;


    @Override
    public void initialize(PasswordVerification constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(this.password);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(this.confirmPassword);
        return passwordValue.equals(confirmPasswordValue);
    }
}
