package com.CstShop.ShopOnlineBackEndMain.entity.basketProduct;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.baskets.Baskets;
import jakarta.persistence.*;

@Entity
@Table(name = "basket_products")
public class BasketProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Products product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "basket_id", nullable = false)
	private Baskets basket;

}
