package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketProductDto {
	private ProductDto productDto;

	private Long quantity;
}
