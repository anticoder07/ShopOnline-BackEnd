package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "attributes")
@Getter
public class Attributes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nameType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Products product;

	@OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<ContentAttributes> contentAttributes;
}
