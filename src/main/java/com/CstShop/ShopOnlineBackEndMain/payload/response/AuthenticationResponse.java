package com.CstShop.ShopOnlineBackEndMain.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	@JsonProperty("accessToken")
	private String accessToken;

	@JsonProperty("roles")
	private String role;
}
