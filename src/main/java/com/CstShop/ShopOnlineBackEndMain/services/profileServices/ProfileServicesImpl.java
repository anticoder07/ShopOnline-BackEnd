package com.CstShop.ShopOnlineBackEndMain.services.profileServices;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.ProfileDto;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.services.UserCirculate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileServicesImpl implements ProfileServices{

	private final UsersRepo usersRepository;

	private final UserCirculate userCirculate;



	@Override
	public ProfileDto takeProfile() {
		ProfileDto profile = new ProfileDto(userCirculate.getValue());
		return profile;
	}

	@Override
	public ProfileDto changeName(String name) {
		Long idCirculate = userCirculate.getValue().getId();
		Users user = usersRepository.changeName(idCirculate, name);
		ProfileDto profile = new ProfileDto(user);
		return profile;
	}

	@Override
	public ProfileDto changePassword(String password) {
		Long idCirculate = userCirculate.getValue().getId();
		Users user = usersRepository.changeName(idCirculate, password);
		ProfileDto profile = new ProfileDto(user);
		return profile;
	}
}
