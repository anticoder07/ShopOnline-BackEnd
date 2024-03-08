package com.CstShop.ShopOnlineBackEndMain.repository.userRepository.basketsRepo;

import com.CstShop.ShopOnlineBackEndMain.entity.users.baskets.Baskets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketsRepo extends JpaRepository<Baskets, Long> {
}
