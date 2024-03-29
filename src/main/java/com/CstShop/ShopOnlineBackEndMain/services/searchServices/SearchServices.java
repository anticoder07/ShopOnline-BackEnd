package com.CstShop.ShopOnlineBackEndMain.services.searchServices;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchServices {
	List<ProductDto> searchAllProductActivity(String input);

	List<BasketProductDto> searchProductFromBasket(String input);

	List<BillDto> searchProductFromBill(String input);

}
