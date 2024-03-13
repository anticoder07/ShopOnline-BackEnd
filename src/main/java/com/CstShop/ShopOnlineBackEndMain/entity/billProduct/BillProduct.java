package com.CstShop.ShopOnlineBackEndMain.entity.billProduct;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "bill_products")
@Getter
public class BillProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long quantity;

	private Double priceOld;

	private Long contentAttributeId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Products product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bill_id", nullable = false)
	private Bills bill;
}
