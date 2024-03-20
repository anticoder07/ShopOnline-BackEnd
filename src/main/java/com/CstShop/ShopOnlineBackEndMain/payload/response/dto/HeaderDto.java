package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeaderDto {
	private String avatar;

	private Long numberOfNotification;
}
