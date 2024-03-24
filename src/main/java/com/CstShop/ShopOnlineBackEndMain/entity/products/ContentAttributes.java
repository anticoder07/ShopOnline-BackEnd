package com.CstShop.ShopOnlineBackEndMain.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_attribute")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentAttributes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String picture;

	private Double price;

	private Long quantity;

	private Long sold;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "attribute_id", nullable = false)
	private Attributes attribute;

	public ContentAttributes(String picture, Double price, Long quantity, Long sold, String content) {
		this.picture = picture;
		this.price = price;
		this.quantity = quantity;
		this.sold = sold;
		this.content = content;
	}

	public ContentAttributes(String picture, Double price, Long quantity, Long sold, String content, Attributes attribute) {
		this.picture = picture;
		this.price = price;
		this.quantity = quantity;
		this.sold = sold;
		this.content = content;
		this.attribute = attribute;
	}
}
