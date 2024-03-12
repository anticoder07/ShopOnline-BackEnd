package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BasketBillServices {
	List<BasketProductDto> addProductToBasket(Long id, Long quantity);

	List<BasketProductDto> deleteProductIntoBasket(Long id);

	List<BasketProductDto> seeAllProductFromBasket();

	List<BillDto> seeAllBill();
}
