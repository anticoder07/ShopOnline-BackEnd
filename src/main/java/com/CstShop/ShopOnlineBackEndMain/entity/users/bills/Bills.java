package com.CstShop.ShopOnlineBackEndMain.entity.users.bills;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
public class Bills {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double total;

	private Date purchaseDate;

	private String name;

	private String phoneNumber;

	private String address;

	@Enumerated(EnumType.STRING)
	private EBillType stateBill;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@OneToMany(mappedBy = "bill", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<BillProduct> billProducts;

	public Bills(Date purchaseDate, String name, String phoneNumber, String address, EBillType stateBill, Users user) {
		this.purchaseDate = purchaseDate;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.stateBill = stateBill;
		this.user = user;
	}
}
