package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductBillItemDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BillDto {
	private Long id;

	private List<ProductBillItemDto> productsList;

	private Date purchasedDate;

	private Double total;

	private String stateBill;

	private String name;

	private String phoneNumber;

	private String address;

	public BillDto(Long id, List<ProductBillItemDto> productsList, Date purchasedDate, String stateBill, String name, String phoneNumber, String address) {
		this.id = id;
		this.productsList = productsList;
		this.purchasedDate = purchasedDate;
		Double tempPrice = 0.0;
		for (ProductBillItemDto product : productsList){
			tempPrice += product.getOldPrice() * product.getQuantity();
		}
		this.total = tempPrice;
		this.stateBill = stateBill;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}
