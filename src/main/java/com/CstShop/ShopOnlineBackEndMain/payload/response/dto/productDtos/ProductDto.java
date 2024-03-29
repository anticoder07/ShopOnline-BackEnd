package com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Long id;

	private String picture;

	private String name;

	private Long sold;

	private Long quantity;

	private String type;

	private String description;

	private Boolean state;

	private Double priceMin;

	private List<AttributeDto> productTypeList;
}
