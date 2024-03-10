package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductTypeItemDto {
	private byte[] picture;

	private Double price;

	private Long quantity;

	private Long sold;
}
