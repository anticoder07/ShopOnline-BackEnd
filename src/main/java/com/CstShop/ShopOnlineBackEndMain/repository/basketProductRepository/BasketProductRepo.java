package com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketProductRepo extends JpaRepository<BasketProduct, Long> {
	List<BasketProduct> findAllByUser(Users user);

	BasketProduct findAllByUserAndProduct(Users users, Products products);
}
