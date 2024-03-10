package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductBillItemDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BillDto {
	private List<ProductBillItemDto> productsList;

	private Date purchasedDate;

	private Double total = 0.0;

	public BillDto(List<ProductBillItemDto> productsList, Date purchasedDate) {
		this.productsList = productsList;
		this.purchasedDate = purchasedDate;
		Double tempPrice = 0.0;
		for (ProductBillItemDto product : productsList){
			tempPrice += product.getOldPrice();
		}
		this.total = tempPrice;
	}
}
