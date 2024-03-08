package com.CstShop.ShopOnlineBackEndMain.entity.products;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	private String name;

	private byte[] picture;

	private Long sold;

	private Long quantity;

	@OneToMany(mappedBy = "products")
	private List<Attributes> attributes;

	@OneToOne
	@JoinColumn(name = "description_id")
	private Descriptions description;

	@OneToOne(mappedBy = "users")
	private ProductTypes type;

	@OneToMany(mappedBy = "products")
	private List<BasketProduct> basketProducts;
}
