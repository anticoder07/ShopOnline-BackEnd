package com.CstShop.repository.userRepository;

import com.CstShop.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Users, Long> {
}
