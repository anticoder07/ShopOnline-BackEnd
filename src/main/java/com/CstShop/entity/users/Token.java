package com.CstShop.entity.users;

import jakarta.persistence.*;

@Entity
@Table(name = "token")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;

	private String token;

	@Enumerated(EnumType.STRING)
	private ETokenType tokenType = ETokenType.BEARER;

	private boolean revoked;

	private boolean expired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

}
