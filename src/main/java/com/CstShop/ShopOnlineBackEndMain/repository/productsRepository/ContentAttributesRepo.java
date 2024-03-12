package com.CstShop.ShopOnlineBackEndMain.repository.productsRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Attributes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ContentAttributes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ContentAttributesRepo extends JpaRepository<ContentAttributes, Long> {
	List<ContentAttributes> findAllByAttribute(Attributes attributes);

	@Modifying
	@Query("""
								update ContentAttributes c set c.picture = :picture, c.price = :price, c.quantity = :quantity, c.sold = :sold where c.id = :id
					""")
	void updateContentAttributes(
					@Param("id") Long id,
					@Param("picture") byte[] picture,
					@Param("price") Double price,
					@Param("quantity") Long quantity,
					@Param("sold") Long sold
	);
}
