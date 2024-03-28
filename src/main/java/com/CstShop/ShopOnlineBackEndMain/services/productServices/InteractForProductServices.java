package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.payload.response.MessageError;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.services.CloudServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractForProductServices {
	private final ContentAttributesRepo contentAttributesRepository;

	private final AttributesRepo attributesRepository;

	private final CloudServices cloudServices;

	public Double findMinPriceOfAttributes(Attributes attributes) {
		List<ContentAttributes> contentAttributesList = attributes.getContentAttributes();

		return contentAttributesList.stream()
						.mapToDouble(ContentAttributes::getPrice)
						.min()
						.orElse(Double.NaN);
	}


	public Double interactAttributes(
					String func,
					AttributeDto attributeDto,
					Attributes attributes,
					Products product,
					Double priceMin
	) {
		double priceMinTemp = 0.0;
		List<ContentAttributes> contentAttributes = new ArrayList<>();
		List<ProductTypeItemDto> productTypeItemDtoList = new ArrayList<>();
		int lengthCA = 0;
		int lengthCADto = 0;
		try {
			contentAttributes = attributes.getContentAttributes();
			lengthCA = contentAttributes.size();
		} catch (Exception ignored) {
		}

		try {
			productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();
			lengthCADto = productTypeItemDtoList.size();
		} catch (Exception ignored) {
		}

		int lengthTemp = Math.min(lengthCA, lengthCADto);
		if (func.equals("change")) {
			for (int i = 0; i < lengthTemp; i++) {
				// change
				ProductTypeItemDto pti = productTypeItemDtoList.get(i);


				ContentAttributes contentAttributesTemp = new ContentAttributes(
								contentAttributes.get(i).getId(),
								cloudServices.uploadPictureCustom(pti.getPicture()),
								pti.getPrice(),
								pti.getQuantity(),
								pti.getSold(),
								pti.getContent(),
								attributes
				);
				interactContentAttributes("change", contentAttributesTemp);

				priceMinTemp = i == 0 ? pti.getPrice() : Math.min(priceMinTemp, pti.getPrice());
			}
			for (int i = lengthTemp; i < lengthCA; i++) {
				// delete
				interactContentAttributes("delete", contentAttributes.get(i));
			}

			for (int i = lengthTemp; i < lengthCADto; i++) {
				// add
				ProductTypeItemDto pti = productTypeItemDtoList.get(i);
				ContentAttributes contentAttributesTemp = new ContentAttributes(
								cloudServices.uploadPictureCustom(pti.getPicture()),
								pti.getPrice(),
								pti.getQuantity(),
								pti.getSold(),
								pti.getContent(),
								attributes
				);

				interactContentAttributes("add", contentAttributesTemp);

				priceMinTemp = Math.min(priceMinTemp, pti.getPrice());
			}

			for (int i = lengthTemp; i < lengthCA; i++) {
				ContentAttributes contentAttributesTemp = new ContentAttributes();
				contentAttributesTemp.setId(productTypeItemDtoList.get(i).getId());
				interactContentAttributes("delete", contentAttributesTemp);
			}

		} else if (func.equals("delete")) {
			for (int i = 0; i < lengthCA; i++) {
				interactContentAttributes("delete", contentAttributes.get(i));
			}
			attributesRepository.deleteById(attributes.getId());

			priceMin = 0.0;
		} else { // add new attribute
			Attributes newAttributes = new Attributes(attributeDto.getType(), product);
			attributesRepository.save(newAttributes);

			for (int i = 0; i < lengthCADto; i++) {
				ContentAttributes newContentAttribute = new ContentAttributes(
								cloudServices.uploadPictureCustom(productTypeItemDtoList.get(i).getPicture()),
								productTypeItemDtoList.get(i).getPrice(),
								productTypeItemDtoList.get(i).getQuantity(),
								productTypeItemDtoList.get(i).getSold(),
								productTypeItemDtoList.get(i).getContent(),
								newAttributes
				);
				contentAttributesRepository.save(newContentAttribute);
				priceMin = Math.min(priceMin, productTypeItemDtoList.get(i).getPrice());
			}
		}
		return priceMin;
	}

	public void interactContentAttributes(String func, ContentAttributes contentAttributes) {
		if (func.equals("change")) {
			ContentAttributes contentAttributesWillChange = contentAttributesRepository.findById(contentAttributes.getId()).orElse(null);
			if (contentAttributesWillChange == null) {
				throw new MessageError("Product no exist");
			}

			String urlIcon = cloudServices.uploadPictureCustom(contentAttributes.getPicture());
			contentAttributesWillChange.setContent(contentAttributes.getContent());
			contentAttributesWillChange.setPicture(urlIcon);
			contentAttributesWillChange.setPrice(contentAttributes.getPrice());
			contentAttributesWillChange.setSold(contentAttributesWillChange.getSold());
			contentAttributesWillChange.setQuantity(contentAttributes.getQuantity());

			contentAttributesRepository.save(contentAttributesWillChange);

		} else if (func.equals("delete")) {
			try {
				contentAttributesRepository.deleteById(contentAttributes.getId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else { // add
			contentAttributesRepository.save(contentAttributes);
		}
	}
}
