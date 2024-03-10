package com.CstShop.ShopOnlineBackEndMain.security.jwt;

import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.TokenRepo;
import com.CstShop.ShopOnlineBackEndMain.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationJwtFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;

	private final UserDetailsServiceImpl userDetailsService;

	private final TokenRepo tokenRepo;

	@Getter
	private static UserDetails userDetailCirculate;

	@Override
	protected void doFilterInternal(
					@NonNull HttpServletRequest request,
					@NonNull HttpServletResponse response,
					@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		try {
			final String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtUtils.extractUserEmail(jwt);
			if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
				userDetailCirculate = userDetails;
				var isTokenValid = tokenRepo.findByToken(jwt)
								.map(t -> !t.isExpired() && !t.isRevoked())
								.orElse(false);
				if (isTokenValid && jwtUtils.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities()
					);
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: " + e);
		}
	}
}
