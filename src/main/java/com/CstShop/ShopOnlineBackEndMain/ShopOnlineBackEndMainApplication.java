package com.CstShop.ShopOnlineBackEndMain;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
@RequiredArgsConstructor
public class ShopOnlineBackEndMainApplication
				implements CommandLineRunner
{

	private final UsersRepo usersRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShopOnlineBackEndMainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Date birthDayUser = new Date();
		Users users = new Users(
						"caobahuong",
						"caobahuong@gmail.com",
						"12345678",
						"0946483158",
						birthDayUser,
						"user"
		);
		usersRepository.save(users);
	}
}
