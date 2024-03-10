package com.CstShop.ShopOnlineBackEndMain.repository.productsRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.billProduct.BillProduct;
import com.CstShop.ShopOnlineBackEndMain.entity.products.ProductTypes;
import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
	@Query("SELECT p FROM Products p")
	List<Products> findAllProducts();

	@Query("select p from Products p order by rand() limit :quantity")
	List<Products> findRandomProduct(@Param("quantity") long quantity);

	List<Products> findAllByType(ProductTypes type);

	List<Products> findAllById(long id);

	Products findAllByBillProducts(BillProduct billProduct);

}
