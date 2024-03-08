package com.CstShop.ShopOnlineBackEndMain.repository.userRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
	Optional<Token> findByToken(String jwt);
}
