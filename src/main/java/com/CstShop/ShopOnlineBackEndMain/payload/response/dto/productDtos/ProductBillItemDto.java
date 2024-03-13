package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductBillItemDto {
	private ProductDto products;

	private Long quantity;

	private Double oldPrice;

	private String type;
}
