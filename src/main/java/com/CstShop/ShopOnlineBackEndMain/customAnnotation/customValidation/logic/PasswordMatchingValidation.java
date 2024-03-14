package com.CstShop.ShopOnlineBackEndMain.customAnnotation.customValidation.logic;

import com.CstShop.ShopOnlineBackEndMain.customAnnotation.customValidation.config.PasswordMatching;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchingValidation implements ConstraintValidator<PasswordMatching, Object> {
	private String password;

	private String confirmPassword;

	@Override
	public void initialize(PasswordMatching matching) {
		this.password = matching.password();
		this.confirmPassword = matching.confirmPassword();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		var passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
		var passwordConfirmValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);
		return (passwordValue != null) ? passwordConfirmValue.equals(password) : confirmPassword == null;
	}
}
