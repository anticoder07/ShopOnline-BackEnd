package com.CstShop.entity.products;

import jakarta.persistence.*;

@Entity
@Table(name = "description")
public class Descriptions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long descriptionId;

	private String content;

	@OneToOne(mappedBy = "description")
	private Products product;
}
