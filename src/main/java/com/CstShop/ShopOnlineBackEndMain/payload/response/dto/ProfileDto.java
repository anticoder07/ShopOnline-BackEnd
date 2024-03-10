package com.CstShop.ShopOnlineBackEndMain.payload.response.dto;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProfileDto {
	private String name;

	private String phoneNumber;

	private Date dateOfBirth;

	private String userEmail;

	public ProfileDto(Users users) {
		this.name = users.getName();
		this.phoneNumber = users.getPhoneNumber();
		this.dateOfBirth = users.getDateOfBirth();
		this.userEmail = users.getUserEmail();
	}
}
