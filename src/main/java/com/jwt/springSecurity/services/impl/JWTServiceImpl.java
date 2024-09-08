package com.jwt.springSecurity.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jwt.springSecurity.services.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5*60*60*1000)) //jwt token is valid for 5 hrs. 
				.signWith(getSigninKey(),  SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 7*60*60*1000)) //refreshToken is valid for 7 hrs
				.signWith(getSigninKey(),  SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Key getSigninKey() {
		byte[] key= Decoders.BASE64.decode("da5b7cc9f110ece37233e7c42ce00b4748304a16533c26c36938055b5ea6cb5e");
		return Keys.hmacShaKeyFor(key);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
		final Claims claims= extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigninKey()).build()
				.parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username= extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

}
