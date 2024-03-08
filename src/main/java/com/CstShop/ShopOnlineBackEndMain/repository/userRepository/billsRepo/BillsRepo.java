package com.CstShop.ShopOnlineBackEndMain.repository.userRepository.billsRepo;

import com.CstShop.ShopOnlineBackEndMain.entity.users.bills.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepo extends JpaRepository<Bills, Long> {
}
