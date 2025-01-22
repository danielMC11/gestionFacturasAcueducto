package com.example.gestionAcueducto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, Object> {
	private String passwordFieldName;
	private String confirmPasswordFieldName;

	@Override
	public void initialize(PasswordConfirmation constraintAnnotation) {
		this.passwordFieldName = constraintAnnotation.password();
		this.confirmPasswordFieldName = constraintAnnotation.confirmPassword();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context){
		try {

			Field field1 = object.getClass().getDeclaredField(passwordFieldName);
			field1.setAccessible(true);
			String password = (String) field1.get(object);

			Field field2 = object.getClass().getDeclaredField(confirmPasswordFieldName);
			field2.setAccessible(true);
			String confirmPassword = (String) field2.get(object);

			return password != null && password.equals(confirmPassword);
		} catch (NoSuchFieldException | IllegalAccessException e) {

			e.printStackTrace();
			return false;
		}
	}

}
