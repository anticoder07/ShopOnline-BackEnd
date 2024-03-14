package com.CstShop.ShopOnlineBackEndMain.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LogInRequest {
	@NotBlank(message = "email cannot blank")
	@Size(max = 50)
	@Email(message = "Email invalidate")
	private String userEmail;

	@NotBlank(message = "password cannot blank")
	private String password;
}