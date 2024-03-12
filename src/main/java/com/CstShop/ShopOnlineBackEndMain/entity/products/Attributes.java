package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "attributes")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

	public Attributes(String nameType, Products product, List<ContentAttributes> contentAttributes) {
		this.nameType = nameType;
		this.product = product;
		this.contentAttributes = contentAttributes;
	}

	public Attributes(String nameType, Products product) {
		this.nameType = nameType;
		this.product = product;
	}
}
