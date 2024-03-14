package com.CstShop.ShopOnlineBackEndMain.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageError extends RuntimeException {
	private String message;
}
