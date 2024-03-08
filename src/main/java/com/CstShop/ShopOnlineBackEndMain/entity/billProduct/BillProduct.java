package com.CstShop.ShopOnlineBackEndMain.entity.billProduct;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import jakarta.persistence.*;

@Entity
@Table(name = "bill_connect_product")
public class BillProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billProductId;

	private Long quantity;

	private Double priceOld;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Products products;
}
