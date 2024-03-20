package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketToBillDto {
	private List<Long> ids;

	private String name;

	private String phoneNumber;

	private String address;
}
