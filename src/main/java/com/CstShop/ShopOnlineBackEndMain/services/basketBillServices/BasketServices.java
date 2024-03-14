package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.payload.response.MessageError;
import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository.BasketProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BasketServices implements BasketBillServices {
	private final UsersRepo usersRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	private final ProductsRepo productsRepository;

	private final BasketProductRepo basketProductRepository;

	private final TakeProductServicesImpl takeProductServices;

	private final ContentAttributesRepo contentAttributesRepository;

	private final AttributesRepo attributesRepository;


	@Override
	public ResponseEntity<Object> addProductToBasket(Long id, Long quantity, Long contentAttributeId) {
		Products products = productsRepository.findAllById(id);
		if (products == null)
			return ResponseHandler.generateErrorResponse(new MessageError("Insufficient quantity of products"));
		ContentAttributes contentAttributes = contentAttributesRepository.findById(contentAttributeId).orElseThrow();
		if (contentAttributes.getQuantity() >= quantity || quantity <= 0) {
			return ResponseHandler.generateErrorResponse(new MessageError("Invalid product quantity"));
		}
		var attributeOfProduct = attributesRepository.findByProductAndContentAttributes(products, contentAttributes);
 		if (attributeOfProduct == null){
			return ResponseHandler.generateErrorResponse(new MessageError("This product does not exist"));
		}
		BasketProduct basketProduct = new BasketProduct(quantity, products, getUser(), contentAttributeId);
		basketProductRepository.save(basketProduct);
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.BAD_REQUEST, seeAllProductFromBasket());
	}

	@Override
	public ResponseEntity<Object> deleteProductIntoBasket(Long id) {
		Products products = productsRepository.findAllById(id);
		if (products == null)
			return ResponseHandler.generateErrorResponse(new MessageError("Insufficient quantity of products"));
		BasketProduct basketProduct = basketProductRepository.findAllByUserAndProduct(getUser(), products);
		if (basketProduct == null)
			return ResponseHandler.generateErrorResponse(new MessageError("This product does not exist"));
		basketProductRepository.delete(basketProduct);
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.BAD_REQUEST, seeAllProductFromBasket());
	}

	@Override
	public List<BasketProductDto> seeAllProductFromBasket() {
		List<BasketProduct> basketProductList = basketProductRepository.findAllByUser(getUser());
		List<BasketProductDto> basketProductDtoList = new ArrayList<>();
		basketProductList.forEach(
						basketProduct -> {
							Products products = basketProduct.getProduct();
							ProductDto productDto = takeProductServices.makeDtoByProducts(Collections.singletonList(products)).get(0);
							ContentAttributes ca = contentAttributesRepository.findById(basketProduct.getContentAttributeId()).orElseThrow();
							basketProductDtoList.add(
											new BasketProductDto(
															productDto,
															basketProduct.getQuantity(),
															ca.getContent(),
															basketProduct.getContentAttributeId()
											)
							);
						}
		);
		return basketProductDtoList;
	}

	@Override
	public List<BillDto> seeAllBill() {
		return null;
	}

	@Override
	public List<BillDto> setStateAdmin(Long id, String state) {
		return null;
	}

	@Override
	public List<BillDto> setStateUser(Long id, String state) {
		return null;
	}
}
