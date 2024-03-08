package com.CstShop.ShopOnlineBackEndMain.auth;

import com.CstShop.ShopOnlineBackEndMain.entity.users.ETokenType;
import com.CstShop.ShopOnlineBackEndMain.entity.users.Token;
import com.CstShop.ShopOnlineBackEndMain.payload.reponse.AuthenticationResponse;
import com.CstShop.ShopOnlineBackEndMain.payload.request.LogInRequest;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.TokenRepo;
import com.CstShop.ShopOnlineBackEndMain.repository.userRepository.UsersRepo;
import com.CstShop.ShopOnlineBackEndMain.security.jwt.JwtUtils;
import com.CstShop.ShopOnlineBackEndMain.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;

	private final UsersRepo userRepository;

	private final TokenRepo tokenRepository;

	private final JwtUtils jwtUtils;

	public AuthenticationResponse logIn(LogInRequest logInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						logInRequest.getUserEmail(),
						logInRequest.getPassword()
		));
		var user = userRepository.findByUserEmail(logInRequest.getUserEmail()).orElseThrow();
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		var jwtToken = jwtUtils.generateToken(userDetails);
		var refreshToken = jwtUtils.generateRefreshToken(userDetails);
		revokeAllUserTokens(userDetails);
		saveUserToken(userDetails, jwtToken);
		return new AuthenticationResponse(
						jwtToken,

	}

	private void saveUserToken(UserDetailsImpl userDetails, String jwtToken) {
		var token = Token.builder()
						.users(userDetails.getUsers())
						.token(jwtToken)
						.tokenType(ETokenType.BEARER)
						.expired(false)
						.revoked(false)
						.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(UserDetailsImpl userDetails) {
		var validUserToken = tokenRepository.findAllValidTokenByUser(userDetails.getUsers().getId());
		if (validUserToken.isEmpty())
			return;
		validUserToken.forEach(
						token -> {
							token.setExpired(true);
							token.setRevoked(true);
						}
		);
		tokenRepository.saveAll(validUserToken);
	}
}
