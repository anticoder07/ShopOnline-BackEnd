package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Descriptions;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.DescriptionsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChangeProductServiceImpl implements ProductServices {
	private final ProductsRepo productsRepository;

	private final DescriptionsRepo descriptionsRepository;

	private final ContentAttributesRepo contentAttributesRepository;

	private final AttributesRepo attributesRepository;

	private final TakeProductServicesImpl takeProductServices;

	@Override
	public List<ProductDto> makeDtoByProducts(List<Products> productsList) {
		return null;
	}

	@Override
	public List<ProductDto> takeProductsToHomePage() {
		return null;
	}

	@Override
	public List<ProductDto> takeProductsByType(String type) {
		return null;
	}

	@Override
	public ProductDto takeInformationProductById(Long id) {
		return null;
	}

	@Override
	public List<ProductDto> takeProductsRecommend(Products productCurrent) {
		return null;
	}

	@Override
	public ProductDto changeProduct(ProductDto productDto) {
		Products products = makeProductsByDto(productDto);
		productsRepository.alterProduct(productDto.getId(), products);
		return takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0);
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		List<AttributeDto> attributeDtoList = productDto.getProductTypeList();

		Products products = new Products(productDto);
		productsRepository.save(products);

		Descriptions descriptions = new Descriptions(productDto.getDescription(), products);
		descriptionsRepository.save(descriptions);

		products.setDescription(descriptions);

		List<Attributes> attributesList = new ArrayList<>();
		attributeDtoList.forEach(
						attributeDto -> {
							List<ProductTypeItemDto> productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();
							List<ContentAttributes> contentAttributesList = new ArrayList<>();
							productTypeItemDtoList.forEach(
											productTypeItemDto -> {
												ContentAttributes contentAttributes = new ContentAttributes(
																productTypeItemDto.getPicture(),
																productTypeItemDto.getPrice(),
																productTypeItemDto.getQuantity(),
																productTypeItemDto.getSold()
												);
												contentAttributesRepository.save(contentAttributes);
												contentAttributesList.add(contentAttributes);
											}
							);
							Attributes attributes = new Attributes(
											attributeDto.getType(),
											products,
											contentAttributesList);
							attributesList.add(attributes);
							attributesRepository.save(attributes);
						}
		);

		products.setAttributes(attributesList);
		productsRepository.save(products);

		return takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0);
	}

	@Override
	public Boolean deleteProduct(ProductDto productDto) {
		try {
			productsRepository.deleteById(productDto.getId());
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public Products makeProductsByDto(ProductDto productDto){
		List<AttributeDto> attributeDtoList = productDto.getProductTypeList();

		Products products = new Products(productDto);

		Descriptions descriptions = new Descriptions(productDto.getDescription(), products);

		products.setDescription(descriptions);

		List<Attributes> attributesList = new ArrayList<>();
		attributeDtoList.forEach(
						attributeDto -> {
							List<ProductTypeItemDto> productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();
							List<ContentAttributes> contentAttributesList = new ArrayList<>();
							productTypeItemDtoList.forEach(
											productTypeItemDto -> {
												ContentAttributes contentAttributes = new ContentAttributes(
																productTypeItemDto.getPicture(),
																productTypeItemDto.getPrice(),
																productTypeItemDto.getQuantity(),
																productTypeItemDto.getSold()
												);
												contentAttributesList.add(contentAttributes);
											}
							);
							Attributes attributes = new Attributes(
											attributeDto.getType(),
											products,
											contentAttributesList);
							attributesList.add(attributes);
						}
		);

		products.setAttributes(attributesList);
		return products;
	}
}
