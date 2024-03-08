package com.CstShop.repository.userRepository.billsRepo;

import com.CstShop.entity.users.bills.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepo extends JpaRepository<Bills, Long> {
}
