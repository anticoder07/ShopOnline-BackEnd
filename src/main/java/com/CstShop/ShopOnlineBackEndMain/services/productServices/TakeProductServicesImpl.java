package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.EProductTypes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.ERole;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TakeProductServicesImpl implements ProductServices {
	private final UsersRepo usersRepository;

	private final ProductsRepo productsRepository;

	private final AttributesRepo attributesRepo;

	private final ContentAttributesRepo contentAttributesRepo;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	@Override
	public List<ProductDto> makeDtoByProducts(List<Products> productsList) {
		List<ProductDto> productDtoList = new ArrayList<>();
		productsList.forEach(
						(products) -> {
							List<Attributes> attributesList = attributesRepo.findAllByProduct(products);
							List<AttributeDto> attributeDtoList = new ArrayList<>();
							final double[] priceMin = {Double.MAX_VALUE};

							if (attributesList.size() != 0) {
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
														if (priceMin[0] > contentAttributes.getPrice())
															priceMin[0] = contentAttributes.getPrice();
													});
													attributeDtoList.add(new AttributeDto(attributes.getId(), attributes.getNameType(), productTypeItemDtoList));
												}
								);
							} else {
								priceMin[0] = products.getPriceMin();
							}

							ProductDto productDto = new ProductDto(
											products.getId(),
											products.getPicture(),
											products.getName(),
											products.getSold(),
											products.getQuantity(),
											products.getType().toString(),
											products.getDescription().getContent(),
											products.getState(),
											priceMin[0],
											attributeDtoList
							);
							productDtoList.add(productDto);
						}
		);
		return productDtoList;
	}

	@Override
	public List<ProductDto> takeProductsToHomePage(String type) {
		Map<String, EProductTypes> mapType = new HashMap<>();
		mapType.put("dien-thoai-phu-kien", EProductTypes.DIENTHOAIPHUKIEN);
		mapType.put("thoi-trang-nam-nu", EProductTypes.THOITRANGNAMNU);
		mapType.put("my-pham-chinh-hang", EProductTypes.MYPHAMCHINHHANG);
		mapType.put("may-tinh-lap-top", EProductTypes.MAYTINHLAPTOP);
		mapType.put("san-pham-khac", EProductTypes.SANPHAMKHAC);

		EProductTypes productType = mapType.getOrDefault(type, EProductTypes.ALL);
		List<Products> productsList = new ArrayList<>();
		try {
			if (getUser().getRole().equals(ERole.ADMIN) && getUser() == null) {
				if (productType.equals(EProductTypes.ALL)) {
					productsList = productsRepository.findAllByState(false);
				} else {
					productsList = productsRepository.findAllByStateAndType(false, productType);
				}
			} else {
				if (productType.equals(EProductTypes.ALL)) {
					productsList = productsRepository.findAllByState(true);
				} else {
					productsList = productsRepository.findAllByStateAndType(true, productType);
				}
			}
		} catch (Exception e) {
			if (productType.equals(EProductTypes.ALL)) {
				productsList = productsRepository.findAllByState(true);
			} else {
				productsList = productsRepository.findAllByStateAndType(true, productType);
			}
		}

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
		return makeDtoByProducts(productsRepository.recommendProduct(12));
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
