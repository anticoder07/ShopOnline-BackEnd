package com.CstShop.ShopOnlineBackEndMain.entity.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Permission;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum ERole {
	USER(Collections.emptySet()),
	ADMIN(Collections.emptySet());

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities(){
		var authorities = getPermissions()
						.stream()
						.map(permission -> new SimpleGrantedAuthority(""))
						.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
