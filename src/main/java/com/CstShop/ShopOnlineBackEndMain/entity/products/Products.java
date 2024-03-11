package com.CstShop.ShopOnlineBackEndMain.entity.products;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.payload.response.dto.productDtos.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private byte[] picture;

	private Long sold;

	private Long quantity;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<Attributes> attributes;

	@OneToOne
	@JoinColumn(name = "description_id")
	private Descriptions description;

	@Enumerated(EnumType.STRING)
	private EProductTypes type = EProductTypes.SANPHAMKHAC;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BasketProduct> basketProducts;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BillProduct> billProducts;

	public Products(ProductDto productDto) {
		this.name = productDto.getName();
		this.picture = productDto.getPicture();
		this.sold = productDto.getSold();
		this.quantity = productDto.getQuantity();
		if (productDto.getType() == "Điện thoại phụ kiện"){
			this.type = EProductTypes.DIENTHOAIPHUKIEN;
		} else if (productDto.getType() == "Máy tính laptop") {
			this.type = EProductTypes.MAYTINHLAPTOP;
		} else if (productDto.getType() == "Thời trang nam nữ") {
			this.type = EProductTypes.THOITRANGNAMNU;
		} else if (productDto.getType() == "Mỹ phẩm chính hãng") {
			this.type = EProductTypes.MYPHAMCHINHHANG;
		} else {
			this.type = EProductTypes.SANPHAMKHAC;
		}
	}
}
