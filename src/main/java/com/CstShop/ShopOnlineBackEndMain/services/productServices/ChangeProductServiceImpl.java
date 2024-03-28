package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.*;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.DescriptionsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.CloudServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChangeProductServiceImpl implements ProductServices {
	private final ProductsRepo productsRepository;

	private final DescriptionsRepo descriptionsRepository;

	private final ContentAttributesRepo contentAttributesRepository;

	private final AttributesRepo attributesRepository;

	private final TakeProductServicesImpl takeProductServices;

	private final CloudServices cloudServices;

	private final InteractForProductServices interactProduct;

	@Override
	public List<ProductDto> makeDtoByProducts(List<Products> productsList) {
		return null;
	}

	@Override
	public List<ProductDto> takeProductsToHomePage(String type) {
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
	public ProductDto changeProduct(
					ProductDto productDto
	) {
		String urlPicture = cloudServices.uploadPictureCustom(productDto.getPicture());

		EProductTypes type = switch (productDto.getType()) {
			case "Điện thoại phụ kiện" -> EProductTypes.DIENTHOAIPHUKIEN;
			case "Máy tính laptop" -> EProductTypes.MAYTINHLAPTOP;
			case "Thời trang nam nữ" -> EProductTypes.THOITRANGNAMNU;
			case "Mỹ phẩm chính hãng" -> EProductTypes.MYPHAMCHINHHANG;
			default -> EProductTypes.SANPHAMKHAC;
		};
		productsRepository.alterProduct(
						productDto.getId(),
						productDto.getName(),
						urlPicture,
						productDto.getSold(),
						productDto.getQuantity(),
						type,
						productDto.getState()
		);

		Products products = productsRepository.findAllById(productDto.getId());

		Descriptions descriptions = descriptionsRepository.findByProduct(products);
		if (descriptions == null) {
			Descriptions description = new Descriptions(
							productDto.getDescription(),
							products
			);
			descriptionsRepository.save(description);
		} else {
			descriptions.setContent(productDto.getDescription());
			descriptionsRepository.save(descriptions);
		}

		List<AttributeDto> attributeDtoList = productDto.getProductTypeList(); // input
		List<Attributes> attributesList = products.getAttributes(); // database
		int minLength = Math.min(attributesList.size(), attributeDtoList.size());

		Double priceMin = products.getPriceMin();

		// Handle "change" action
		for (int i = 0; i < minLength; i++) {
			priceMin = interactProduct.interactAttributes("change", attributeDtoList.get(i), attributesList.get(i), null, priceMin);
		}

		// Handle "add" action for remaining attributes
		for (int i = minLength; i < attributeDtoList.size(); i++) {
			priceMin = interactProduct.interactAttributes("add", attributeDtoList.get(i), null, products, priceMin);

		}

		// Handle "delete" action for remaining attributes
		for (int i = minLength; i < attributesList.size(); i++) {
			priceMin = interactProduct.interactAttributes("delete", null, attributesList.get(i), null, priceMin);
		}

		products.setPriceMin(priceMin);
		productsRepository.save(products);
		return takeProductServices.makeDtoByProducts(List.of(products)).get(0);
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		String urlPicture = "";
		if (productDto.getPicture() != null) {
			urlPicture = cloudServices.uploadPictureByBase64(productDto.getPicture());
		}

		productDto.setPicture(urlPicture);

		Products products = new Products(productDto);
		productsRepository.save(products);

		Descriptions descriptions = new Descriptions(productDto.getDescription(), products);
		descriptionsRepository.save(descriptions);

		products.setDescription(descriptions);
		productsRepository.save(products);

		Double[] priceMin = new Double[1];
		priceMin[0] = productDto.getPriceMin();

		List<AttributeDto> attributeDtoList = productDto.getProductTypeList();
//		if (attributeDtoList.size() > 0) {
		List<Attributes> attributesList = new ArrayList<>();
		attributeDtoList.forEach(attributeDto -> {
			Attributes attributes = new Attributes(attributeDto.getType(), products);
			attributesRepository.save(attributes);
			attributesList.add(attributes);

			List<ProductTypeItemDto> productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();

			for (ProductTypeItemDto item : productTypeItemDtoList) {
				String urlPictureType = "";
				if (!item.getPicture().equals("")) {
					urlPictureType = cloudServices.uploadPictureByBase64(item.getPicture());
				}

				Double price = priceMin[0];
				if (item.getPrice() != null)
					price = item.getPrice();

				ContentAttributes contentAttributes = new ContentAttributes(
								urlPictureType,
								price,
								item.getQuantity(),
								item.getSold(),
								item.getContent(),
								attributes
				);
				if (priceMin[0] > price) {
					priceMin[0] = price;
				}
				contentAttributesRepository.save(contentAttributes);
			}

		});

		products.setAttributes(attributesList);
//		}

		products.setPriceMin(priceMin[0]);
		productsRepository.save(products);

		return takeProductServices.makeDtoByProducts(List.of(products)).get(0);
	}


	@Override
	public Boolean deleteProduct(Long id) {
		try {
			productsRepository.updateState(id, false);
			return true;
		} catch (DataAccessException e) {
			log.error("Lỗi khi xóa sản phẩm: " + e.getMessage());
			return false;
		}
	}

	public Products makeProductsByDto(ProductDto productDto) {
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
																productTypeItemDto.getSold(),
																productTypeItemDto.getContent()
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
