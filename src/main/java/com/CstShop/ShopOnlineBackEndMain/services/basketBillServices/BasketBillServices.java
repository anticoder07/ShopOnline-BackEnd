package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BillDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BasketBillServices {
	ResponseEntity<Object> addProductToBasket(Long id, Long quantity, Long contentAttributeId);

	ResponseEntity<Object> deleteProductIntoBasket(Long id);

	List<BasketProductDto> seeAllProductFromBasket();

	ResponseEntity<Object> transportProductFromBasketToBill(List<Long> ids, String name, String phoneNumber, String address);

	List<BillDto> seeAllBill();

	List<BillDto> setStateAdmin(Long id, String state);

	List<BillDto> setStateUser(Long id, String state);
}
