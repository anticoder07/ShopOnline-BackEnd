package com.CstShop.ShopOnlineBackEndMain.myController;

import com.CstShop.ShopOnlineBackEndMain.payload.response.ResponseHandler;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.ProfileDto;
import com.CstShop.ShopOnlineBackEndMain.services.BASE64DecodedMultipartFile;
import com.CstShop.ShopOnlineBackEndMain.services.HeaderService;
import com.CstShop.ShopOnlineBackEndMain.services.profileServices.ProfileServicesImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileServicesImpl profileServices;

	private final HeaderService headerService;

	@GetMapping("/header")
	public ResponseEntity<Object> getInformationHeader() {
		try {
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, headerService.takeInformationHeader());
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@GetMapping("/see")
	public ResponseEntity<Object> seeProfile() {
		try {
			ProfileDto profileDto = profileServices.takeProfile();
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, profileDto);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/change/name")
	public ResponseEntity<Object> changeNameProfile(@RequestBody String name) {
		try {
			ProfileDto profileDto = profileServices.changeName(name);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, profileDto);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping("/change/password")
	public ResponseEntity<Object> changePasswordProfile(@RequestBody String pwd) {
		try {
			ProfileDto profileDto = profileServices.changePassword(pwd);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, profileDto);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}

	@PostMapping(value = "/change/avatar")
	public ResponseEntity<Object> changeAvatarProfile(@RequestBody String base64String) {
		try {
			ProfileDto profileDto = profileServices.changeAvatar(base64String);
			return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, profileDto);
		} catch (Exception e) {
			return ResponseHandler.generateErrorResponse(e);
		}
	}
}
