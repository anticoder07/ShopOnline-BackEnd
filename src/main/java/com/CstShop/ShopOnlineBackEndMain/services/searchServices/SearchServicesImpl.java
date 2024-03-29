package com.CstShop.ShopOnlineBackEndMain.services.searchServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillAndBasketDto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductBillItemDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.basketBillServices.BasketServices;
import com.CstShop.ShopOnlineBackEndMain.services.basketBillServices.BillServices;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServicesImpl implements SearchServices {
	private final ProductsRepo productsRepository;

	private final BasketServices basketServices;

	private final BillServices billServices;

	private final TakeProductServicesImpl takeProductServices;

	private static String removeAccents(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(normalized).replaceAll("").toLowerCase();
	}

	@Override
	public List<ProductDto> searchAllProductActivity(String input) {
//		String lowerCaseInput = removeAccents(input).toLowerCase();
//
//		List<Products> products = productsRepository.findAllByState(true);
//
//		List<Products> productsList = products.stream()
//						.filter(item -> removeAccents(item.getName().toLowerCase()).contains(lowerCaseInput))
//						.collect(Collectors.toList());
		List<Products> productsList = productsRepository.searchProductsByNameAndState(input);

		return takeProductServices.makeDtoByProducts(productsList);
	}

	@Override
	public List<BasketProductDto> searchProductFromBasket(String input) {
		String lowerCaseInput = removeAccents(input).toLowerCase();

		List<BasketProductDto> basketProductDtoList = basketServices.seeAllProductFromBasket();
		List<BasketProductDto> basketProductDtoListSearch = new ArrayList<>();
		basketProductDtoList.forEach(
						basketProductDto -> {
							if (removeAccents(basketProductDto.getProductDto().getName()).contains(lowerCaseInput)) {
								basketProductDtoListSearch.add(basketProductDto);
							}
						}
		);

		return basketProductDtoListSearch;
	}

	public List<BillDto> searchProductFromBill(String input) {
		String lowerCaseInput = removeAccents(input).toLowerCase();

		List<BillDto> billDtoList = billServices.seeAllBill();
		List<BillDto> billDtoSearch = new ArrayList<>();
		billDtoList.forEach(
						billDto -> {
							if (billDto.getPurchasedDate().toString().contains(lowerCaseInput)) {
								billDtoSearch.add(billDto);
							} else {
								List<ProductBillItemDto> productList = billDto.getProductsList();

								productList.forEach(productBillItemDto -> {
									ProductDto productDto = productBillItemDto.getProducts();
									if (productDto.getName().toLowerCase().contains(lowerCaseInput)){
										billDtoSearch.add(billDto);
									}
								});
							}
						}
		);

		return billDtoSearch;
	}
}
