package com.CstShop.entity.products;

import jakarta.persistence.*;

@Entity
@Table(name = "content_attributes")
public class ContentAttributes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contentAttributeId;

	private byte[] picture;

	private Double price;

	private Long quantity;

	private Long sold;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_id")
	private Attributes attribute;
}
