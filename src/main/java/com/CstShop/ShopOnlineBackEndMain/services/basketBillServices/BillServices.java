package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductBillItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.billProductRepository.BillProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ContentAttributesRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo.BillsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BillServices implements BasketBillServices {

	private final UsersRepo usersRepository;

	private final BillsRepo billsRepository;

	private final ProductsRepo productRepository;

	private final BillProductRepo billProductRepository;

	private final TakeProductServicesImpl takeProductServices;

	private final ContentAttributesRepo contentAttributesRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	@Override
	public List<BasketProductDto> addProductToBasket(Long id, Long quantity, Long contentAttributeId) {
		return null;
	}

	@Override
	public List<BasketProductDto> deleteProductIntoBasket(Long id) {
		return null;
	}

	@Override
	public List<BasketProductDto> seeAllProductFromBasket() {
		return null;
	}

	@Override
	public List<BillDto> seeAllBill() {
		List<BillDto> billDtoList = new ArrayList<>();
		List<Bills> billsList = billsRepository.findBillsByUser(getUser());
		billsList.forEach((billItem) -> {

							List<BillProduct> billProductList = billProductRepository.findAllByBill(billItem);
							List<ProductBillItemDto> productBillItemList = new ArrayList<>();
							billProductList.stream().forEach((billProductItem) -> {
								Products products = productRepository.findAllByBillProducts(billProductItem);
								ContentAttributes ca = contentAttributesRepository.findById(billProductItem.getContentAttributeId()).orElseThrow();
								productBillItemList.add(new ProductBillItemDto(
												takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0),
												billProductItem.getQuantity(),
												billProductItem.getPriceOld(),
												ca.getContent()
								));
							});
							billDtoList.add(new BillDto(
									productBillItemList,
									billItem.getPurchaseDate()
							));
						}
		);
		return billDtoList;
	}
}
