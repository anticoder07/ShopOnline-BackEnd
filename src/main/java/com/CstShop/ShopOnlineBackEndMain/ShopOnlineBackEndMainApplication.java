package com.CstShop.ShopOnlineBackEndMain;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
public class ShopOnlineBackEndMainApplication
//				implements CommandLineRunner
{

//	private final UsersRepo usersRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShopOnlineBackEndMainApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", "dfh3tjyzr");
		config.put("api_key", "921517586189564");
		config.put("api_secret", "o6_d4wCHHdqWpi_HHZOd_2a7IQ4");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Date birthDayUser = new Date();
//		Users users = new Users(
//						"caobahuong",
//						"caobahuong@gmail.com",
//						"12345678",
//						"0946483158",
//						birthDayUser,
//						"user"
//		);
//		usersRepository.save(users);
//	}
}
