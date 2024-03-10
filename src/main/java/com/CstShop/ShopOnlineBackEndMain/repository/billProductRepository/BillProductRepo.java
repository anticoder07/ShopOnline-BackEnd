package com.CstShop.ShopOnlineBackEndMain.repository.billProductRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface 	BillProductRepo extends JpaRepository<BillProduct, Long> {
	List<BillProduct> findAllByBill(Bills bills);
}
