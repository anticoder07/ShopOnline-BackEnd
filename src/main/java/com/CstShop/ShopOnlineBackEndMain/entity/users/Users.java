package com.CstShop.ShopOnlineBackEndMain.entity.users;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String userEmail;

	private String password;

	private String phoneNumber;

	private LocalDate dateOfBirth;

	private String avatar;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<Token> tokens;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<Bills> bills;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BasketProduct> basketProducts;

	public Users(
					String name,
					String userEmail,
					String password,
					String phoneNumber,
					LocalDate dateOfBirth,
					String role,
					String avatar
	) {
		this.name = name;
		this.userEmail = userEmail;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		if (role.equals("admin"))
			setRole(ERole.ADMIN);
		else if (role.equals("user")) {
			setRole(ERole.USER);
		}
		if (role == null){
			setRole(ERole.USER);
		}
		this.avatar = avatar;
	}
}
