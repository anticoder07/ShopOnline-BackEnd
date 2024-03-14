package com.CstShop.ShopOnlineBackEndMain.entity.users.bills;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
@Data
public class Bills {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double total;

	private Date purchaseDate;

	@Enumerated(EnumType.STRING)
	private EBillType stateBill;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@OneToMany(mappedBy = "bill", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BillProduct> billProducts;
}
