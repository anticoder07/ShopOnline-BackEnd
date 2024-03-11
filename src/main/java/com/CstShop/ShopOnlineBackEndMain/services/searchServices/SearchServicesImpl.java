package com.CstShop.ShopOnlineBackEndMain.services.searchServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo.BillsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchServicesImpl implements SearchServices{
	private final ProductsRepo productsRepository;

	private final TakeProductServicesImpl takeProductServices;

	@Override
	public List<ProductDto> searchProduct(String input) {
		List<Products> productsList = productsRepository.searchProductsByName(input);
		return takeProductServices.makeDtoByProducts(productsList);
	}

	@Override
	public List<BillDto> searchProductFromBill(String input) {
		List<Products> productsList = productsRepository.searchProductsByName(input);
		return null;
	}

	@Override
	public List<BasketProductDto> searchProductFromBasket(String input) {
		return null;
	}
}
