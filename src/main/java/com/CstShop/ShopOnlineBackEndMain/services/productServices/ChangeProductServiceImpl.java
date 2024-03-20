package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.*;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.DescriptionsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
		EProductTypes type;
		if (productDto.getType().equals("Điện thoại phụ kiện")) {
			type = EProductTypes.DIENTHOAIPHUKIEN;
		} else if (productDto.getType().equals("Máy tính laptop")) {
			type = EProductTypes.MAYTINHLAPTOP;
		} else if (productDto.getType().equals("Thời trang nam nữ")) {
			type = EProductTypes.THOITRANGNAMNU;
		} else if (productDto.getType().equals("Mỹ phẩm chính hãng")) {
			type = EProductTypes.MYPHAMCHINHHANG;
		} else {
			type = EProductTypes.SANPHAMKHAC;
		}
		productsRepository.alterProduct(productDto.getId(), productDto.getName(), productDto.getPicture(), productDto.getSold(), productDto.getQuantity(), type, productDto.getState());

		Products products = productsRepository.findAllById(productDto.getId());

		Descriptions descriptions = descriptionsRepository.findByProduct(products);
		descriptions.setContent(productDto.getDescription());
		descriptionsRepository.save(descriptions);

		List<AttributeDto> attributeDtoList = productDto.getProductTypeList();
		List<Attributes> attributesList = products.getAttributes();

		int lengthAttribute = attributeDtoList.size();
		if (attributeDtoList.size() > attributesList.size())
			lengthAttribute = attributesList.size();

		for (int i = 0; i < lengthAttribute; i++) {
			attributesList.get(i).setNameType(attributeDtoList.get(i).getType());
			attributesRepository.save(attributesList.get(i));

			List<ProductTypeItemDto> productTypeItemDtoList = attributeDtoList.get(i).getProductTypeItemDtoList();
			List<ContentAttributes> contentAttributesList = attributesList.get(i).getContentAttributes();
			int lengthType = productTypeItemDtoList.size();
			if (productTypeItemDtoList.size() > contentAttributesList.size()) {
				lengthType = contentAttributesList.size();
			}
			for (int j = 0; j < lengthType; j++) {
				contentAttributesRepository.updateContentAttributes(
								contentAttributesList.get(j).getId(),
								productTypeItemDtoList.get(j).getPicture(),
								productTypeItemDtoList.get(j).getPrice(),
								productTypeItemDtoList.get(j).getQuantity(),
								productTypeItemDtoList.get(j).getSold(),
								productTypeItemDtoList.get(j).getContent()
				);
			}
			for (int j = lengthType; j < contentAttributesList.size(); j++) {
				// delete content attributes redundant
				try {
					contentAttributesRepository.deleteById(contentAttributesList.get(j).getId());
				} catch (Exception e) {
					log.error("error: " + e);
				}
			}
			for (int j = lengthType; j < productTypeItemDtoList.size(); j++) {
				// make and save content attributes redundant
				ContentAttributes contentAttributes = new ContentAttributes(
								productTypeItemDtoList.get(j).getPicture(),
								productTypeItemDtoList.get(j).getPrice(),
								productTypeItemDtoList.get(j).getQuantity(),
								productTypeItemDtoList.get(j).getSold(),
								productTypeItemDtoList.get(j).getContent()
				);
				contentAttributes.setAttribute(attributesList.get(i));
				contentAttributesRepository.save(contentAttributes);
			}

			attributesRepository.save(attributesList.get(i));
		}
		for (int i = lengthAttribute; i < attributesList.size(); i++) {
			List<ContentAttributes> contentAttributesList = attributesList.get(i).getContentAttributes();
			contentAttributesList.forEach(
							contentAttributes -> {
								contentAttributesRepository.deleteById(contentAttributes.getId());
							}
			);
			attributesRepository.deleteById(attributesList.get(i).getId());
		}

		for (int i = lengthAttribute; i < attributeDtoList.size(); i++) {
			Attributes attributes = new Attributes(
							attributeDtoList.get(i).getType(),
							products
			);
			attributesRepository.save(attributes);

			List<ProductTypeItemDto> productTypeItemDtoList = attributeDtoList.get(i).getProductTypeItemDtoList();
			for (int j = 0; j < productTypeItemDtoList.size(); j++) {
				ContentAttributes contentAttributes = new ContentAttributes(
								productTypeItemDtoList.get(j).getPicture(),
								productTypeItemDtoList.get(j).getPrice(),
								productTypeItemDtoList.get(j).getQuantity(),
								productTypeItemDtoList.get(j).getSold(),
								productTypeItemDtoList.get(j).getContent()
				);
				contentAttributes.setAttribute(attributes);
				contentAttributesRepository.save(contentAttributes);
			}
		}

		productsRepository.save(products);
		return takeProductServices.makeDtoByProducts(List.of(products)).get(0);
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		Products products = new Products(productDto);
		productsRepository.save(products);

		Descriptions descriptions = new Descriptions(productDto.getDescription(), products);
		descriptionsRepository.save(descriptions);

		products.setDescription(descriptions);
		productsRepository.save(products);

		List<AttributeDto> attributeDtoList = productDto.getProductTypeList();
		if (attributeDtoList.size() < 1){
			List<Attributes> attributesList = new ArrayList<>();
			attributeDtoList.forEach(
							attributeDto -> {
								Attributes attributes = new Attributes(attributeDto.getType(), products);
								attributesRepository.save(attributes);
								List<ProductTypeItemDto> productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();
								productTypeItemDtoList.forEach(
												item -> {
													ContentAttributes contentAttributes = new ContentAttributes(
																	item.getPicture(),
																	item.getPrice(),
																	item.getQuantity(),
																	item.getSold(),
																	item.getContent()
													);
													contentAttributes.setAttribute(attributes);
													contentAttributesRepository.save(contentAttributes);
												}
								);
								attributesRepository.save(attributes);
							}
			);

			products.setAttributes(attributesList);
		} else {
				products.setPriceMin(productDto.getPriceMin());
		}
		productsRepository.save(products);

		return takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0);
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
