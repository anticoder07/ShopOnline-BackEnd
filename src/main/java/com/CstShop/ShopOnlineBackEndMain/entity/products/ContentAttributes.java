package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;

@Entity
@Table(name = "content_attribute")
public class ContentAttributes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private byte[] picture;

	private Double price;

	private Long quantity;

	private Long sold;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "attribute_id", nullable = false)
	private Attributes attribute;
}
