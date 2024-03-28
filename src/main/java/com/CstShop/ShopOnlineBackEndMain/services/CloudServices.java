package com.CstShop.ShopOnlineBackEndMain.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudServices {
	private static final int BASE64_INPUT = 1;

	private static final int HOLLOW_INPUT = 2;

	private static final int EXISTING_IMAGE = 3;

	private static final int INVALID_INPUT = 0;

	private final Cloudinary cloudinary;

	public boolean isBase64(String input) {
		try {
			String base64String = input.split(",")[1].trim();

			byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64String);
			String reencodedString = java.util.Base64.getEncoder().encodeToString(decodedBytes);

			return reencodedString.equals(base64String);
		} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
			return false;
		}
	}

	public boolean isHollow(String input){
		return input.equals("");
	}

	public boolean isImageExists(String imageUrl) {
		try {
			this.cloudinary.api().resource(imageUrl, ObjectUtils.emptyMap());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int filterInputType(String input) {
		if (isBase64(input)) {
			return BASE64_INPUT;
		}
		if (isHollow(input)) {
			return HOLLOW_INPUT;
		}
//		if (isImageExists(input)) {
			return EXISTING_IMAGE;
//		}
//		return INVALID_INPUT;
	}

	public String uploadPictureByBase64(String base64String) {
		try {
			byte[] decodedBytes = Base64.decodeBase64(base64String.split(",")[1].trim());
			MultipartFile multipartFile = new BASE64DecodedMultipartFile(decodedBytes);
			return uploadPictureByFile(multipartFile);
		} catch (Exception e) {
			throw new RuntimeException("Failed to decode base64 string or create MultipartFile", e);
		}
	}

	public String uploadPictureByFile(MultipartFile file) {
		try {
			Map<String, Object> data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			if (data.containsKey("url")) {
				return (String) data.get("url");
			} else {
				throw new RuntimeException("Failed to upload image to Cloudinary. Response: " + data.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read file bytes or upload to Cloudinary", e);
		}
	}

	public String uploadPictureCustom(String input) {
		int filterResult = filterInputType(input);

		switch (filterResult) {
			case BASE64_INPUT:
				return uploadPictureByBase64(input);
			case EXISTING_IMAGE:
				return input;
			default:
				return "";
		}
	}

}
