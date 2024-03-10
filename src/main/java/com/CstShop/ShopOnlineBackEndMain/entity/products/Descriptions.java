package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "description")
@Getter
public class Descriptions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@OneToOne(mappedBy = "description")
	private Products product;
}
