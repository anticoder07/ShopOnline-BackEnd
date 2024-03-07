package com.CstShop.entity.basketProduct;

import com.CstShop.entity.products.Products;
import com.CstShop.entity.users.baskets.Baskets;
import jakarta.persistence.*;

@Entity
@Table(name = "basket_product")
public class BasketProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long basketProductId;

	private Long quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basket_id")
	private Baskets basket;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Products product;

}
