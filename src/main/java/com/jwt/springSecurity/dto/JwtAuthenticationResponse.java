package com.jwt.springSecurity.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	
	private String token;
	private String refreshToken;
	
}
