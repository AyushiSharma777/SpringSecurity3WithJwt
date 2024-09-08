package com.jwt.springSecurity.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//@Service
public interface JWTService {
	
	 String generateToken(UserDetails userDetails);
	 String extractUsername(String token);
	 boolean isTokenValid(String token, UserDetails userDetails);
	 String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
