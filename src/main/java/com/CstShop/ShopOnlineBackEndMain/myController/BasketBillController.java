package com.CstShop.ShopOnlineBackEndMain.myController;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BasketProductDto;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.BillDto;
import com.CstShop.ShopOnlineBackEndMain.services.basketBillServices.BasketServices;
import com.CstShop.ShopOnlineBackEndMain.services.basketBillServices.BillServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequiredArgsConstructor
public class BasketBillController {
	private final BasketServices basketServices;

	private final BillServices billServices;

	@PostMapping("/basket/add")
	public ResponseEntity<Object> addProductToBasket(@RequestBody Long id, @RequestBody Long quantity) {
		try {
			List<BasketProductDto> productDtoList = basketServices.addProductToBasket(id, quantity);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, productDtoList);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("basket/delete")
	public ResponseEntity<Object> deleteProductIntoBasket(@RequestBody Long id) {
		try {
			List<BasketProductDto> productDtoList = basketServices.deleteProductIntoBasket(id);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, productDtoList);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("/basket/see")
	public ResponseEntity<Object> seeAllProductFromBasket() {
		try {
			List<BasketProductDto> productDtoList = basketServices.seeAllProductFromBasket();
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, productDtoList);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("bill/see")
	public ResponseEntity<Object> seeAllBill() {
		try {
			List<BillDto> billDtoList = billServices.seeAllBill();
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, billDtoList);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
