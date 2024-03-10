package com.CstShop.ShopOnlineBackEndMain.services;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.security.jwt.AuthenticationJwtFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class UserCirculate {
	private final UsersRepo usersRepository;
	private UserDetails userDetailsCirculate = AuthenticationJwtFilter.getUserDetailCirculate();

	private Users initializationUserCirculate(){
		Users usersCirculate = usersRepository
						.findByUserEmail(userDetailsCirculate.getUsername())
						.orElseThrow();
		return usersCirculate;
	}

	@Getter
	private Users value = initializationUserCirculate();
}
