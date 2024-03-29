package com.CstShop.ShopOnlineBackEndMain.auth;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.request.LogInRequest;
import com.CstShop.ShopOnlineBackEndMain.payload.request.SignUpRequest;
import com.CstShop.ShopOnlineBackEndMain.security.services.LogOutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	private final LogOutService logOutService;

	@PostMapping("/log-in")
	public ResponseEntity<Object> logIn(@RequestBody LogInRequest logInRequest){
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, authenticationService.logIn(logInRequest));
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/sign-up")
	public Object signUp(@RequestBody SignUpRequest signUpRequest){
		try {
			return authenticationService.signUp(signUpRequest);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("/log-out")
	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		logOutService.logout(request, response, null);
	}
}
