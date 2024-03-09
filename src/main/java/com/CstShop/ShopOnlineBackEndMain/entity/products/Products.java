package com.CstShop.ShopOnlineBackEndMain.entity.products;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private byte[] picture;

	private Long sold;

	private Long quantity;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<Attributes> attributes;

	@OneToOne
	@JoinColumn(name = "description_id")
	private Descriptions description;

	@OneToOne(mappedBy = "product")
	private ProductTypes type;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BasketProduct> basketProducts;
}
