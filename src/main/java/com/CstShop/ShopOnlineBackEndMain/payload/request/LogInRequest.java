package com.CstShop.ShopOnlineBackEndMain.payload.request;

import lombok.Data;

@Data
public class LogInRequest {
	private String userEmail;

	private String password;
}