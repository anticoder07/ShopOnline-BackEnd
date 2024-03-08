package com.CstShop.ShopOnlineBackEndMain.payload.request;

import lombok.Data;

@Data
public class SignUpRequest {
	private String username;

	private String email;

	private String password;

	private String confirmPassword;

	private String sdt;

	private String role;
}
