package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TakeProductServicesImpl implements TakeProductServices {
	private final ProductsRepo productsRepository;

	private final AttributesRepo attributesRepo;

	private final ContentAttributesRepo contentAttributesRepo;

	@Override
	public List<ProductDto> makeDtoByProducts(List<Products> productsList){
		List<ProductDto> productDtoList = new ArrayList<>();
		productsList.forEach(
						(products) -> {
							List<Attributes> attributesList = attributesRepo.findAllByProduct(products);
							List<AttributeDto> attributeDtoList = new ArrayList<>();
							attributesList.forEach(
											(attributes) -> {
												List<ContentAttributes> contentAttributesList = contentAttributesRepo.findAllByAttribute(attributes);
												List<ProductTypeItemDto> productTypeItemDtoList = new ArrayList<>();
												contentAttributesList.forEach(contentAttributes -> {
													productTypeItemDtoList.add(new ProductTypeItemDto(
																	contentAttributes.getPicture(),
																	contentAttributes.getPrice(),
																	contentAttributes.getQuantity(),
																	contentAttributes.getSold()
													));
												});
												attributeDtoList.add(new AttributeDto(attributes.getNameType(), productTypeItemDtoList));
											}
							);
							ProductDto productDto = new ProductDto(
											products.getPicture(),
											products.getName(),
											products.getSold(),
											products.getQuantity(),
											products.getType().getType(),
											products.getDescription().getContent(),
											attributeDtoList
							);
							productDtoList.add(productDto);
						}
		);
		return productDtoList;
	}

	@Override
	public List<ProductDto> takeProductsToHomePage() {
		List<Products> productsList = productsRepository.findAllProducts();
		return makeDtoByProducts(productsList);
	}

	@Override
	public List<ProductDto> takeProductsByType(String type) {
		List<Products> productsList = productsRepository.findAllByType(null);
		return makeDtoByProducts(productsList);
	}

	@Override
	public List<ProductDto> takeInformationProductById(Long id) {
		List<Products> productsList = productsRepository.findAllById(id);
		return makeDtoByProducts(productsList);
	}

	@Override
	public List<ProductDto> takeProductsRecommend(Products productCurrent) {
		return null;
	}
}
