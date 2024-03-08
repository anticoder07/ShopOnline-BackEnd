package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;

@Entity
@Table(name = "product_type")
public class ProductTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productTypeId;

	private String type;

	@OneToOne
	@JoinColumn(name = "user_id")
	private Products product;
}
