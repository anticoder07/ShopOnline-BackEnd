package com.CstShop.ShopOnlineBackEndMain;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
public class ShopOnlineBackEndMainApplication {
	@Value("${application.CstShop.ShopOnlineBackEndMainApplication.cloudName}")
	private String cloudName;

	@Value("${application.CstShop.ShopOnlineBackEndMainApplication.apiKey}")
	private String apiKey;

	@Value("${application.CstShop.ShopOnlineBackEndMainApplication.apiSecret}")
	private String apiSecret;

	public static void main(String[] args) {
		SpringApplication.run(ShopOnlineBackEndMainApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

}
