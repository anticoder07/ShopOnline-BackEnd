package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.EBillType;
import com.CstShop.ShopOnlineBackEndMain.payload.response.MessageError;
import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository.BasketProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.billProductRepository.BillProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.AttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo.BillsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

	private final BillsRepo billsRepository;

	private final BillProductRepo billProductRepository;


	@Override
	public ResponseEntity<Object> addProductToBasket(Long id, Long quantity, Long contentAttributeId) {
		Products products = productsRepository.findById(id).orElse(null);
		if (products == null)
			return ResponseHandler.generateErrorResponse(new MessageError("Insufficient quantity of products"));
		ContentAttributes contentAttributes = contentAttributesRepository.findById(contentAttributeId).orElse(null);
		if (contentAttributes.getQuantity() < quantity || quantity <= 0) {
			return ResponseHandler.generateErrorResponse(new MessageError("Invalid product quantity"));
		}
		var attributeOfProduct = attributesRepository.findByProductAndContentAttributes(products, contentAttributes).orElse(null);
		if (attributeOfProduct == null) {
			return ResponseHandler.generateErrorResponse(new MessageError("This product does not exist"));
		}
		BasketProduct basketProductCurrent = basketProductRepository.findAllByUserAndProduct(getUser(), products);
		if (basketProductCurrent != null){
			if (basketProductCurrent.getQuantity() + quantity > contentAttributes.getQuantity()){
				return ResponseHandler.generateErrorResponse(new MessageError("Invalid product quantity"));
			} else {
				basketProductCurrent.setQuantity(basketProductCurrent.getQuantity() + quantity);
				basketProductRepository.save(basketProductCurrent);
			}
		} else {
			BasketProduct basketProduct = new BasketProduct(quantity, products, getUser(), contentAttributeId);
			basketProductRepository.save(basketProduct);
		}
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, seeAllProductFromBasket());
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
															basketProduct.getId(),
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
	public ResponseEntity<Object> transportProductFromBasketToBill(
					List<Long> ids,
					String name,
					String phoneNumber,
					String address
	) {
		Bills bills = new Bills(
						new Date(),
						name,
						phoneNumber,
						address,
						EBillType.PENDING_APPROVAL,
						getUser()
		);
		billsRepository.save(bills);
		ids.forEach((id) -> {
			BasketProduct basketProduct = basketProductRepository.findById(id).orElse(null);
			ContentAttributes contentAttributes = contentAttributesRepository.findById(basketProduct.getContentAttributeId()).orElse(null);
			productsRepository.updateQuantity(basketProduct.getProduct().getId(), basketProduct.getProduct().getQuantity() - basketProduct.getQuantity());
			BillProduct billProduct = new BillProduct(
							basketProduct.getQuantity(),
							contentAttributes.getPrice(),
							basketProduct.getContentAttributeId(),
							basketProduct.getProduct(),
							bills
			);
			billProductRepository.save(billProduct);
			basketProductRepository.delete(basketProduct);
		});

		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, seeAllProductFromBasket());

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
