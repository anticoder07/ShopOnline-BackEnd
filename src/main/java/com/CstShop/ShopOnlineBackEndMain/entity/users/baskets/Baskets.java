package com.CstShop.ShopOnlineBackEndMain.entity.users.baskets;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "baskets")
public class Baskets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long basketId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

	@OneToMany(mappedBy = "baskets")
	private List<BasketProduct> basketProducts;
}
