package com.CstShop.repository.productsRepository;

import com.CstShop.entity.products.ContentAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentAttributesRepo extends JpaRepository<ContentAttributes, Long> {
}
