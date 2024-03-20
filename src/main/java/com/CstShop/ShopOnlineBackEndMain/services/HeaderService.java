package com.CstShop.ShopOnlineBackEndMain.services;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.HeaderDto;
import com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository.BasketProductRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeaderService {
	private final UsersRepo usersRepository;

	private final BasketProductRepo basketProductRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	public HeaderDto takeInformationHeader() {
			Users users = usersRepository.findById(getUser().getId()).orElse(null);
			if (users == null){
				return null;
			}
			String avatar = users.getAvatar();
			Long countBasketByUser = basketProductRepository.countByUser(users);
			return new HeaderDto(
							avatar,
							countBasketByUser
			);
	}
}
