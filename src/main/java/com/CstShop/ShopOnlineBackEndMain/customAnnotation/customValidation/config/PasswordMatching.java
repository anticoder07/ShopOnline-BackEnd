package com.CstShop.ShopOnlineBackEndMain.customAnnotation.customValidation.config;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatching {
	String password();

	String confirmPassword();

	String message() default "Passwords must match!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
