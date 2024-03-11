package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository.BasketProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.UserCirculate;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class BasketServices implements BasketBillServices {
	private final ProductsRepo productsRepository;

	private final BasketProductRepo basketProductRepository;

	private final UserCirculate userCirculate;

	private final TakeProductServicesImpl takeProductServices;


	@Override
	public List<BasketProductDto> addProductToBasket(Long id, Long quantity) {
		Products products = productsRepository.findAllById(id);
		if (products == null)
			return null;
		BasketProduct basketProduct = new BasketProduct(quantity, products, userCirculate.getValue());
		basketProductRepository.save(basketProduct);
		return seeAllProductFromBasket();
	}

	@Override
	public List<BasketProductDto> deleteProductIntoBasket(Long id) {
		Products products = productsRepository.findAllById(id);
		if (products == null)
			return null;
		BasketProduct basketProduct = basketProductRepository.findAllByUserAndProduct(userCirculate.getValue(), products);
		basketProductRepository.delete(basketProduct);
		return seeAllProductFromBasket();
	}

	@Override
	public List<BasketProductDto> seeAllProductFromBasket() {
		List<BasketProduct> basketProductList = basketProductRepository.findAllByUser(userCirculate.getValue());
		List<BasketProductDto> basketProductDtoList = new ArrayList<>();
		basketProductList.forEach(
						basketProduct -> {
							Products products = basketProduct.getProduct();
							ProductDto productDto = takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0);
							basketProductDtoList.add(
											new BasketProductDto(
															productDto,
															basketProduct.getQuantity()
											)
							);
						}
		);
		return basketProductDtoList;
	}

	@Override
	public List<BillDto> seeAllBill() {
		return null;
	}
}
