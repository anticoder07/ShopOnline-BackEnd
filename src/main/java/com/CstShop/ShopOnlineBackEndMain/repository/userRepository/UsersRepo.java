package com.CstShop.ShopOnlineBackEndMain.repository.userRepository;

import com.CstShop.ShopOnlineBackEndMain.entity.users.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UsersRepo extends JpaRepository<Users, Long> {
	Optional<Users> findById(Users users);

	Optional<Users> findByUserEmail(String userEmail);
	Boolean existsByUserEmail(String userEmail);

	@Modifying
	@Query("update Users u set u.name = :name where u.id = :id")
	void changeName(@Param("id") Long id, @Param("name") String name);

	@Modifying
	@Query("update Users u set u.password = :pwd where u.id = :id")
	void changePassword(@Param("id") Long id, @Param("pwd") String password);

	@Modifying
	@Query("update Users u set u.avatar = :avatar where u.id = :id")
	void changeAvatar(@Param("id") Long id, @Param("avatar") String avatar);
}
