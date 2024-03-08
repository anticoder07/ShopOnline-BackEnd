package com.CstShop.ShopOnlineBackEndMain.security.services;

import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {
	private final TokenRepo tokenRepository;

	@Override
	public void logout(
					HttpServletRequest request,
					HttpServletResponse response,
					Authentication authentication
	) {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader.isEmpty() || !authHeader.startsWith("Bearer "))
			return;
		final String jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt).orElse(null);
		if (storedToken != null){
			storedToken.setRevoked(false);
			storedToken.setExpired(false);
			tokenRepository.save(storedToken);
			SecurityContextHolder.clearContext();
		}
	}
}
