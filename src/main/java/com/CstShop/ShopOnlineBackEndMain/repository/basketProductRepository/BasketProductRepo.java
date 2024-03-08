package com.CstShop.ShopOnlineBackEndMain.repository.basketProductRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.basketProduct.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductRepo extends JpaRepository<BasketProduct, Long> {
}
