package com.CstShop.ShopOnlineBackEndMain.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudServices {
	private final Cloudinary cloudinary;

	public String uploadPictureByBase64(String base64String) {
		byte[] decodedBytes = Base64.decodeBase64(base64String.split(",")[1]);

		MultipartFile multipartFile = new BASE64DecodedMultipartFile(decodedBytes);

		return uploadPictureByFile(multipartFile);
	}


	public String uploadPictureByFile(MultipartFile file) {
		try {
			Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			String url = (String) data.get("url");
			return url;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
