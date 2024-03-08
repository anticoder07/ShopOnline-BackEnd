package com.CstShop.repository.productsRepository;

import com.CstShop.entity.products.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypesRepo extends JpaRepository<ProductTypes, Long> {
}
