package com.CstShop.ShopOnlineBackEndMain.services.searchServices;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchServices {
	List<ProductDto> searchProduct(String input);

	List<BasketProductDto> searchProductFromBasket(String input);

}
