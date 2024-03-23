package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AttributeDto {
	private Long id;

	private String type;

	private List<ProductTypeItemDto> productTypeItemDtoList;
}
