package com.CstShop.repository.productsRepository;

import com.CstShop.entity.products.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributesRepo extends JpaRepository<Attributes, Long> {
}
