package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {
	private byte[] picture;

	private String name;

	private Long sold;

	private Long quantity;

	private String type;

	private String description;

	private List<AttributeDto> productTypeList;
}
