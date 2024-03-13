package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;

import java.util.List;

public interface BasketBillServices {
	List<BasketProductDto> addProductToBasket(Long id, Long quantity, Long contentAttributeId);

	List<BasketProductDto> deleteProductIntoBasket(Long id);

	List<BasketProductDto> seeAllProductFromBasket();

	List<BillDto> seeAllBill();
}
