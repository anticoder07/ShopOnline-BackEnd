package com.CstShop.entity.users;

import com.CstShop.entity.users.baskets.Baskets;
import com.CstShop.entity.users.bills.Bills;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;

	private String userEmail;

	private String password;

	private String phoneNumber;

	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	private ERole role;

	@OneToMany(mappedBy = "users")
	private List<Token> token;

	@OneToMany(mappedBy = "users")
	private List<Bills> bills;

	@OneToMany(mappedBy = "users")
	private List<Baskets> baskets;
}
