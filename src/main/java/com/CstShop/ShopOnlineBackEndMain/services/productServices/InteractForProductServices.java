package com.CstShop.ShopOnlineBackEndMain.services.productServices;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.payload.response.MessageError;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.AttributeDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductTypeItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.services.CloudServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractForProductServices {
	private final ContentAttributesRepo contentAttributesRepository;

	private final AttributesRepo attributesRepository;

	private final CloudServices cloudServices;

	public String isBase64(String input) {
		try {
			String base64String = input.split(",")[1].trim();

			byte[] decodedBytes = Base64.getDecoder().decode(base64String);

			String reencodedString = Base64.getEncoder().encodeToString(decodedBytes);

			if (reencodedString.equals(base64String)) {
				return input;
			} else {
				return "";
			}
		}
		catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
			return "";
		}
	}

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
					Double priceMin
	) {
		double priceMinTemp = 0.0;
		List<ContentAttributes> contentAttributes = attributes.getContentAttributes();
		List<ProductTypeItemDto> productTypeItemDtoList = attributeDto.getProductTypeItemDtoList();

		int lengthCA = contentAttributes.size();
		int lengthCADto = productTypeItemDtoList.size();
		System.out.println(lengthCA);
		int lengthTemp = Math.min(lengthCA, lengthCADto);
		if (func.equals("change")) {
			for (int i = 0; i < lengthTemp; i++) {
				// change
				ProductTypeItemDto pti = productTypeItemDtoList.get(i);


				ContentAttributes contentAttributesTemp = new ContentAttributes(
								contentAttributes.get(i).getId(),
								"",
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
				Long ptiId = productTypeItemDtoList.get(i).getId();
				ContentAttributes ca = new ContentAttributes();
				ca.setId(ptiId);
				interactContentAttributes("delete", ca);
			}

			for (int i = lengthTemp; i < lengthCADto; i++) {
				// add
				ProductTypeItemDto pti = productTypeItemDtoList.get(i);
				ContentAttributes contentAttributesTemp = new ContentAttributes(
								"",
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
			priceMin = Double.NaN;
		} else {
			contentAttributesRepository.saveAll(contentAttributes);
			attributesRepository.save(attributes);
			priceMin = Math.min(priceMin, findMinPriceOfAttributes(attributes));
		}
		return priceMin;
	}

	public void interactContentAttributes(String func, ContentAttributes contentAttributes) {
		if (func.equals("change")) {
			ContentAttributes contentAttributesWillChange = contentAttributesRepository.findById(contentAttributes.getId()).orElse(null);
			if (contentAttributesWillChange == null) {
				throw new MessageError("Product no exist");
			}

//			String urlIcon = cloudServices.uploadPictureByBase64(isBase64(contentAttributes.getPicture()));
String urlIcon = "";
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
