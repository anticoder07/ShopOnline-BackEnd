package com.CstShop.ShopOnlineBackEndMain.myController.productController;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import com.CstShop.ShopOnlineBackEndMain.services.productServices.ChangeProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
public class ChangeProductController {
	private final ChangeProductServiceImpl changeProductService;

	@PostMapping("/change")
	public ResponseEntity<Object> changeProduct(@RequestBody ProductDto productDto){
		try {
			ProductDto product = changeProductService.changeProduct(productDto);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, product);
		} catch (Exception e){
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto){
		try {
			ProductDto product = changeProductService.addProduct(productDto);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, product);
		} catch (Exception e){
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<Object> deleteProduct(@RequestParam("i") Long id){
		try {
			boolean check = changeProductService.deleteProduct(id);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, check);
		} catch (Exception e){
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
