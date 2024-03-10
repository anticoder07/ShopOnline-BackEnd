package com.CstShop.ShopOnlineBackEndMain.services.profileServices;

import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.ProfileDto;
import org.springframework.stereotype.Service;

@Service
public interface ProfileServices {
	ProfileDto takeProfile();

	ProfileDto changeName(String name);

	ProfileDto changePassword(String password);

}
