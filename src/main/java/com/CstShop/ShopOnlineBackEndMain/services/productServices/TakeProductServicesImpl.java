package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.EProductTypes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TakeProductServicesImpl implements ProductServices {
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
																	contentAttributes.getId(),
																	contentAttributes.getPicture(),
																	contentAttributes.getPrice(),
																	contentAttributes.getQuantity(),
																	contentAttributes.getSold(),
																	contentAttributes.getContent()
													));
												});
												attributeDtoList.add(new AttributeDto(attributes.getId(), attributes.getNameType(), productTypeItemDtoList));
											}
							);
							ProductDto productDto = new ProductDto(
											products.getId(),
											products.getPicture(),
											products.getName(),
											products.getSold(),
											products.getQuantity(),
											products.getType().toString(),
											products.getDescription().getContent(),
											products.getState(),
											attributeDtoList
							);
							productDtoList.add(productDto);
						}
		);
		return productDtoList;
	}

	@Override
	public List<ProductDto> takeProductsToHomePage() {
		List<Products> productsList = productsRepository.findAllByState(true);
		return makeDtoByProducts(productsList);
	}

	@Override
	public List<ProductDto> takeProductsByType(String typeInput) {
		EProductTypes type;
		if (typeInput.equals("dien-thoai-phu-kien")) {
			type = EProductTypes.DIENTHOAIPHUKIEN;
		} else if (typeInput.equals("may-tinh-laptop")) {
			type = EProductTypes.MAYTINHLAPTOP;
		} else if (typeInput.equals("thoi-trang-nam-nu")) {
			type = EProductTypes.THOITRANGNAMNU;
		} else if (typeInput.equals("my-pham-chinh-hang")) {
			type = EProductTypes.MYPHAMCHINHHANG;
		} else {
			type = EProductTypes.SANPHAMKHAC;
		}
		List<Products> productsList = productsRepository.findAllByType(type);
		return makeDtoByProducts(productsList);
	}

	@Override
	public ProductDto takeInformationProductById(Long id) {
		Products productsList = productsRepository.findAllById(id);
		return makeDtoByProducts(Arrays.asList(productsList)).get(0);
	}

	@Override
	public List<ProductDto> takeProductsRecommend(Products productCurrent) {
		return null;
	}

	@Override
	public ProductDto changeProduct(ProductDto productDto) {
		return null;
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		return null;
	}

	@Override
	public Boolean deleteProduct(Long id) {
		return null;
	}
}
