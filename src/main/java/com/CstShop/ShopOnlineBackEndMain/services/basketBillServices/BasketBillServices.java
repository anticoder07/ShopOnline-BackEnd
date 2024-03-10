package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketBillServices {
	List<BasketProduct> addProductToBasket(Long id);

	List<BasketProduct> deleteProductIntoBasket(Long id);

	List<BasketProduct> seeAllProductFromBasket();

	List<BillDto> seeAllBill();
}
