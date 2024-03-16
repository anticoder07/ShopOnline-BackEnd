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
@RequestMapping("api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequiredArgsConstructor
public class BasketBillController {
	private final BasketServices basketServices;

	private final BillServices billServices;

	@PostMapping("/basket/add")
	public ResponseEntity<Object> addProductToBasket(@RequestParam String i
//					, @RequestParam Long q, @RequestParam Long t
	) {
//		System.out.println(i + " " + q + " " + t);
		System.out.println(i);
		try {
			return null;
//			return basketServices.addProductToBasket(i, q, t);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("basket/delete")
	public ResponseEntity<Object> deleteProductIntoBasket(@RequestParam("i") Long id) {
		try {
			return basketServices.deleteProductIntoBasket(id);
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

	@PostMapping("bill/set-state/admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Object> setStateAdmin(@RequestBody Long id, @RequestBody String state){
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, billServices.setStateAdmin(id, state));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("bill/set-state")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Object> setStateUser(@RequestBody Long id, @RequestBody String state){
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, billServices.setStateUser(id, state));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
