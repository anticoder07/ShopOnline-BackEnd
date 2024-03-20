package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketProductDto {
	private Long id;

	private ProductDto productDto;

	private Long quantity;

	private String type;

	private Long typeId;
}
