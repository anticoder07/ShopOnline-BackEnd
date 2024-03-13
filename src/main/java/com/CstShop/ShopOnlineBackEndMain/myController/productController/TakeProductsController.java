package com.CstShop.ShopOnlineBackEndMain.myController.productController;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.TakeProductServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/take/product")
@RequiredArgsConstructor
public class TakeProductsController {
	private final TakeProductServicesImpl takeProductServices;

	@GetMapping("/home-page")
	public ResponseEntity<Object> takeProductHomePage() {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeProductsToHomePage());
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/by/type")
	public ResponseEntity<Object> takeProductsByType(@RequestParam String t) {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeProductsByType(t));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/by/id")
	public ResponseEntity<Object> takeProductsByType(@RequestBody long i) {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeInformationProductById(i));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
