package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductTypeItemDto {
	private Long id;

	private byte[] picture;

	private Double price;

	private Long quantity;

	private Long sold;

	private String content;
}
