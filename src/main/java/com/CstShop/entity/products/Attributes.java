package com.CstShop.entity.products;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "attributes")
public class Attributes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attributeId;

	private String nameType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Products products;

	@OneToMany(mappedBy = "attributes")
	private List<ContentAttributes> contentAttributes;
}
