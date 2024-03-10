package com.CstShop.ShopOnlineBackEndMain.services.basketBillServices;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductBillItemDto;
import com.CstShop.ShopOnlineBackEndMain.repository.billProductRepository.BillProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.productsRepository.ProductsRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo.BillsRepo;
import com.CstShop.ShopOnlineBackEndMain.services.UserCirculate;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class BillServices implements BasketBillServices {
	private final UserCirculate userCirculate;

	private final BillsRepo billsRepository;

	private final ProductsRepo productRepository;

	private final BillProductRepo billProductRepository;

	private final TakeProductServicesImpl takeProductServices;

	@Override
	public List<BasketProduct> addProductToBasket(Long id) {
		return null;
	}

	@Override
	public List<BasketProduct> deleteProductIntoBasket(Long id) {
		return null;
	}

	@Override
	public List<BasketProduct> seeAllProductFromBasket() {
		return null;
	}

	@Override
	public List<BillDto> seeAllBill() {
		List<BillDto> billDtoList = new ArrayList<>();
		List<Bills> billsList = billsRepository.findBillsByUser(userCirculate.getValue());
		billsList.forEach((billItem) -> {

							List<BillProduct> billProductList = billProductRepository.findAllByBill(billItem);
							List<ProductBillItemDto> productBillItemList = new ArrayList<>();
							billProductList.stream().forEach((billProductItem) -> {
								Products products = productRepository.findAllByBillProducts(billProductItem);
								productBillItemList.add(new ProductBillItemDto(
												takeProductServices.makeDtoByProducts(Arrays.asList(products)).get(0),
												billProductItem.getQuantity(),
												billProductItem.getPriceOld()
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
