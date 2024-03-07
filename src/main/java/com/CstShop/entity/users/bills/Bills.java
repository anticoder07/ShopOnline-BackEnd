package com.CstShop.entity.users.bills;

import com.CstShop.entity.billProduct.BillProduct;
import com.CstShop.entity.users.Users;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bills {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billId;

	private Double total;

	private Date purchaseDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

	@OneToMany(mappedBy = "bills")
	private List<BillProduct> billProducts;
}
