package com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo;

import com.CstShop.ShopOnlineBackEndMain.entity.products.Products;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillsRepo extends JpaRepository<Bills, Long> {
	List<Bills> findBillsByUser(Users users);

	Optional<Bills> findByIdAndUser(Long id, Users users);
}
