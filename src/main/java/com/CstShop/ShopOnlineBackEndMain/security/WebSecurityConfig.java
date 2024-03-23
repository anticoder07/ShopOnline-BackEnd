package com.CstShop.ShopOnlineBackEndMain.security;

import com.CstShop.ShopOnlineBackEndMain.security.jwt.AuthEntryPointJwt;
import com.CstShop.ShopOnlineBackEndMain.security.jwt.AuthenticationJwtFilter;
import com.CstShop.ShopOnlineBackEndMain.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
	private final AuthEntryPointJwt unAuthorizationHandler;

	private final UserDetailsServiceImpl userDetailsService;

	private final AuthenticationJwtFilter authenticationJwtFilter;

	private final LogoutHandler logoutHandler;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
						.csrf(csrf -> csrf.disable())
						.exceptionHandling(exception -> exception.authenticationEntryPoint(unAuthorizationHandler))
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.authorizeHttpRequests(request -> request
										.requestMatchers(
														"/api/auth/*",
														"api/take/product/*",
														"api/take/product/by/type",
														"api/take/product/by/id",
														"api/take/product/home-page/*"
										)
										.permitAll()
										.anyRequest().authenticated())
						.authenticationProvider(daoAuthenticationProvider())
						.addFilterBefore(authenticationJwtFilter, UsernamePasswordAuthenticationFilter.class)
						.logout(logout -> logout.logoutUrl("api/log-out")
										.addLogoutHandler(logoutHandler)
										.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

		return http.build();
	}
}
