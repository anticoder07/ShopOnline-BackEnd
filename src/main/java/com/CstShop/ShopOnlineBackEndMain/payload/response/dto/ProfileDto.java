package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class ProfileDto {
	private String name;

	private String phoneNumber;

	private LocalDate dateOfBirth;

	private String userEmail;

	private String avatar;

	public ProfileDto(Users users) {
		this.name = users.getName();
		this.phoneNumber = users.getPhoneNumber();
		this.dateOfBirth = users.getDateOfBirth();
		this.userEmail = users.getUserEmail();
		this.avatar = users.getAvatar();
	}
}
