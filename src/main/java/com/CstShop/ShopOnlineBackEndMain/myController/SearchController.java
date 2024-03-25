package com.CstShop.ShopOnlineBackEndMain.myController;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.services.searchServices.SearchServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/search")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class SearchController {
	private final SearchServicesImpl searchServices;

	@GetMapping("/{name}")
	public ResponseEntity<Object> searchAllProductActivity(@PathVariable("name") String name) {
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, searchServices.searchAllProductActivity(name));
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/basket/{name}")
	public ResponseEntity<Object> searchAllProductBasket(@PathVariable("name") String name) {
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, searchServices.searchProductFromBasket(name));
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/bill/{name}")
	public ResponseEntity<Object> searchAllProductBill(@PathVariable("name") String name) {
		return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, searchServices.searchProductFromBill(name));
	}
}
