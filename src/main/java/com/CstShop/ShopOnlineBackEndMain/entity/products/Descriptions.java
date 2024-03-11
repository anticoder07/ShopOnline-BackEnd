package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "description")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Descriptions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@OneToOne(mappedBy = "description")
	private Products product;

	public Descriptions(String content, Products product) {
		this.content = content;
		this.product = product;
	}
}

