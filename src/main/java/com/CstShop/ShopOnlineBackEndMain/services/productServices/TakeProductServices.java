package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TakeProductServices {
	List<ProductDto> makeDtoByProducts(List<Products> productsList);

	List<ProductDto> takeProductsToHomePage();

	List<ProductDto> takeProductsByType(String type);

	List<ProductDto> takeInformationProductById(Long id);

	List<ProductDto> takeProductsRecommend(Products productCurrent);
}
