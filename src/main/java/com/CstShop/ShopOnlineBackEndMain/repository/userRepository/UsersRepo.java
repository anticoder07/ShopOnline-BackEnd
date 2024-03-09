package com.CstShop.ShopOnlineBackEndMain.repository.userRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
	Optional<Users> findByUserEmail(String userEmail);

	Boolean existsByUserEmail(String userEmail);
}
