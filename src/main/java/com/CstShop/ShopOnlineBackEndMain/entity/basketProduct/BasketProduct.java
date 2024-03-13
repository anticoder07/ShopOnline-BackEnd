package com.CstShop.ShopOnlineBackEndMain.entity.basketProduct;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "basket_products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasketProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long quantity;

	private Long contentAttributeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Products product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	public BasketProduct(Long quantity, Products product, Users users, Long contentAttributeId) {
		this.quantity = quantity;
		this.product = product;
		this.user = users;
		this.contentAttributeId = contentAttributeId;
	}
}
