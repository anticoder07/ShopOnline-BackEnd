package com.CstShop.ShopOnlineBackEndMain.services.searchServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.basketBillServices.BasketServices;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SearchServicesImpl implements SearchServices {
	private final ProductsRepo productsRepository;

	private final BasketServices basketServices;

	private final TakeProductServicesImpl takeProductServices;

	@Override
	public List<ProductDto> searchProduct(String input) {
		List<Products> productsList = productsRepository.searchProductsByName(input);
		return takeProductServices.makeDtoByProducts(productsList);
	}

	@Override
	public List<BasketProductDto> searchProductFromBasket(String input) {
		List<BasketProductDto> basketProductDtoList = basketServices.seeAllProductFromBasket();
		List<BasketProductDto> basketProductDtoListSearch = new ArrayList<>();
		basketProductDtoList.forEach(
						basketProductDto -> {
							if (basketProductDto.getProductDto().getName().contains(input)) {
								basketProductDtoListSearch.add(basketProductDto);
							}
						}
		);

		return basketProductDtoListSearch;
	}
}
