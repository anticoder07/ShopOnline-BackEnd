package com.CstShop.ShopOnlineBackEndMain.repository.productsRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.EProductTypes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductsRepo extends JpaRepository<Products, Long> {
	@Query("SELECT p FROM Products p")
	List<Products> findAllProducts();

	List<Products> findAllByType(EProductTypes type);

	List<Products> findAllByState(Boolean state);

	Products findAllById(long id);

	Products findAllByBillProducts(BillProduct billProduct);

	@Modifying
	@Query("""
							update Products p set p.name = :name, p.picture = :picture, p.sold = :sold, p.quantity = :quantity, p.type = :typeProduct, p.state = :state where p.id = :id
					""")
	void alterProduct(
					@Param("id") Long id,
					@Param("name") String name,
					@Param("picture") byte[] picture,
					@Param("sold") Long sold,
					@Param("quantity") Long quantity,
					@Param("typeProduct") EProductTypes type,
					@Param("state") Boolean state
					);

	List<Products> searchProductsByName(String name);

	@Modifying
	@Query("""
							update Products p set p.state = :state where p.id = :id
					""")
	void updateState(@Param("id") Long id, @Param("state") Boolean state);
}
