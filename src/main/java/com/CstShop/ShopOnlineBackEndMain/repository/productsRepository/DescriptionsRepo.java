package com.CstShop.ShopOnlineBackEndMain.repository.productsRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Descriptions;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionsRepo extends JpaRepository<Descriptions, Long> {
	Descriptions findByProduct (Products products);
}
