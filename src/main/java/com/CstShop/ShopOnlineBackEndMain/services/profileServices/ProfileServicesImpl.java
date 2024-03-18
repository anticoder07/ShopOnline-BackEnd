package com.CstShop.ShopOnlineBackEndMain.services.profileServices;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.ProfileDto;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServicesImpl implements ProfileServices {
	private final UsersRepo usersRepository;

	private final PasswordEncoder passwordEncoder;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	@Override
	public ProfileDto takeProfile() {
		ProfileDto profile = new ProfileDto(getUser());
		return profile;
	}

	@Override
	public ProfileDto changeName(String name) {
		System.out.println(name);
		Long idCirculate = getUser().getId();
		usersRepository.changeName(idCirculate, name);
		Users user = usersRepository.findById(idCirculate).orElseThrow();
		System.out.println(user);
		ProfileDto profile = new ProfileDto(user);
		return profile;
	}

	@Override
	public ProfileDto changePassword(String password) {
		Long idCirculate = getUser().getId();
		usersRepository.changePassword(idCirculate, passwordEncoder.encode(password));
		Users user = usersRepository.findById(idCirculate).orElseThrow();
		ProfileDto profile = new ProfileDto(user);
		return profile;
	}

	@Override
	public ProfileDto changeAvatar(String avatar) {
		Long idCirculate = getUser().getId();
		usersRepository.changeName(idCirculate, avatar);
		Users user = usersRepository.findById(idCirculate).orElseThrow();
		ProfileDto profile = new ProfileDto(user);
		return profile;
	}
}
