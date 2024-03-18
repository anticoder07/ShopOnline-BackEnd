package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasketToBillDto {
	private List<Long> ids;

	private String name;

	private String phoneNumber;

	private String address;
}
