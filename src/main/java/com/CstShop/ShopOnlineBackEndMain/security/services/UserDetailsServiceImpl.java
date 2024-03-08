package com.CstShop.ShopOnlineBackEndMain.security.services;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UsersRepo usersRepo;

	@Override
	public UserDetails loadUserByUsername(String userGmail) throws UsernameNotFoundException {
		Users users = usersRepo.findByUserEmail(userGmail)
						.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userGmail));
		UserDetails userDetails = new UserDetailsImpl(users);
		return userDetails;
	}
}
