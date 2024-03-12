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
import org.springframework.dao.EmptyResultDataAccessException;
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

		int mark;
		if (attributeDtoList.size() > attributesList.size())
			mark = attributesList.size();
		else
			mark = attributeDtoList.size();

		for (int i = 0; i < mark; i++) {
			attributesList.get(i).setNameType(attributeDtoList.get(i).getType());
			List<ProductTypeItemDto> productTypeItemDtoList = attributeDtoList.get(i).getProductTypeItemDtoList();
			List<ContentAttributes> contentAttributesList = attributesList.get(i).getContentAttributes();
			for (int j = 0; j < productTypeItemDtoList.size(); j++) {
				if (j >= contentAttributesList.size()) {
					ProductTypeItemDto pt = productTypeItemDtoList.get(j);
					ContentAttributes ca = new ContentAttributes(
									pt.getPicture(),
									pt.getPrice(),
									pt.getQuantity(),
									pt.getSold()
					);
					ca.setAttribute(attributesList.get(i));
					contentAttributesRepository.save(ca);
				} else {
					ContentAttributes ca = contentAttributesList.get(j);
					contentAttributesRepository.updateContentAttributes(
									ca.getId(),
									ca.getPicture(),
									ca.getPrice(),
									ca.getQuantity(),
									ca.getSold()
					);
				}
			}
			attributesRepository.save(attributesList.get(i));
		}
		for (int i = mark; i < attributesList.size(); i++){
			attributesRepository.deleteById(attributesList.get(i).getId());
		}

		for (int i = mark; i < attributeDtoList.size(); i++){
			Attributes attributes = new Attributes(
							attributeDtoList.get(i).getType(),
							products
			);
			attributesRepository.save(attributes);

			List<ProductTypeItemDto> productTypeItemDtoList = attributeDtoList.get(i).getProductTypeItemDtoList();
			for (int j = 0; j < productTypeItemDtoList.size(); j++){
				ContentAttributes contentAttributes = new ContentAttributes(
								productTypeItemDtoList.get(j).getPicture(),
								productTypeItemDtoList.get(j).getPrice(),
								productTypeItemDtoList.get(j).getQuantity(),
								productTypeItemDtoList.get(j).getSold()
				);
				contentAttributes.setAttribute(attributes);
				contentAttributesRepository.save(contentAttributes);
			}
		}

		productsRepository.save(products);
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
			Products products = productsRepository.findAllById(productDto.getId());
			products.setState(false);
			return true;
		} catch (EmptyResultDataAccessException e) {
			log.error("Không tìm thấy sản phẩm với id: " + productDto.getId());
			return false;
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
