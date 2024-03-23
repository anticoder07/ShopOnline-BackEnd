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

	@GetMapping("/home-page/{type}")
	public ResponseEntity<Object> takeProductHomePage(@PathVariable String type) {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeProductsToHomePage(type));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("/by/type")
	public ResponseEntity<Object> takeProductsByType(@RequestParam("t") String type) {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeProductsByType(type));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("/by/id")
	public ResponseEntity<Object> takeProductsByType(@RequestParam("i") long id) {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, takeProductServices.takeInformationProductById(id));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
