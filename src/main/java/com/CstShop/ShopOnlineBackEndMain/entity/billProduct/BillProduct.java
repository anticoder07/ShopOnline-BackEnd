package com.CstShop.ShopOnlineBackEndMain.entity.billProduct;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill_products")
@Getter
@NoArgsConstructor
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

	public BillProduct(Long quantity, Double priceOld, Long contentAttributeId, Products product, Bills bill) {
		this.quantity = quantity;
		this.priceOld = priceOld;
		this.contentAttributeId = contentAttributeId;
		this.product = product;
		this.bill = bill;
	}
}
