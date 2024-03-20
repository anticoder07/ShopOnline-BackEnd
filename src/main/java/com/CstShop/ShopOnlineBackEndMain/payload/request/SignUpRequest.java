package com.CstShop.ShopOnlineBackEndMain.payload.request;

import com.CstShop.ShopOnlineBackEndMain.customAnnotation.customValidation.config.PasswordMatching;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@PasswordMatching(
				password = "password",
				confirmPassword = "confirmPassword",
				message = "Passwords do not match!"
)
public class SignUpRequest {
	@NotBlank(message = "username cannot blank")
	@Size(min = 3, max = 20, message = "size digits username from 3 to 20")
	private String username;

	@NotBlank(message = "email cannot blank")
	@Size(max = 50)
	@Email(message = "Email invalidate")
	private String email;

	@NotBlank(message = "password cannot blank")
	private String password;

	@NotBlank(message = "confirm password cannot blank")
	private String confirmPassword;

	@NotBlank(message = "phone number cannot blank")
	@Size(min = 9, max = 11, message = "phone number min 9 max 11")
	private String sdt;

	private String role;

	@NotBlank(message = "date of birth cannot blank")
	private LocalDate	 dateOfBirth;

	private String avatar = null;
}
