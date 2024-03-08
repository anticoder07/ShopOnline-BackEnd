package com.CstShop.ShopOnlineBackEndMain.repository.billProductRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillProductRepo extends JpaRepository<BillProduct, Long> {
}
