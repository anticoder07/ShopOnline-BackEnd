package com.CstShop.ShopOnlineBackEndMain.repository.productsRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AttributesRepo extends JpaRepository<Attributes, Long> {
	List<Attributes> findAllByProduct(Products products);

	@Modifying
	@Query("""
							delete  Attributes a where a.id = ?1 
					""")
	void deleteById(Long id);

	Optional<Attributes> findByProductAndContentAttributes(Products products, ContentAttributes contentAttributes);
}
