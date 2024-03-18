package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketAddDto {
	private Long id;

	private Long quantity;

	private Long type;
}
