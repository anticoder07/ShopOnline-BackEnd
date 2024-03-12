package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {
	private Long id;

	private byte[] picture;

	private String name;

	private Long sold;

	private Long quantity;

	private String type;

	private String description;

	private Boolean state;

	private List<AttributeDto> productTypeList;
}
