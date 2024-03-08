package com.CstShop.repository.basketProductRepository;

import com.CstShop.entity.basketProduct.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductRepo extends JpaRepository<BasketProduct, Long> {
}
