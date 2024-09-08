package com.jwt.springSecurity.services.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.springSecurity.dto.JwtAuthenticationResponse;
import com.jwt.springSecurity.dto.RefreshTokenRequest;
import com.jwt.springSecurity.dto.SigninRequest;
import com.jwt.springSecurity.dto.SignUpRequest;
import com.jwt.springSecurity.entities.Role;
import com.jwt.springSecurity.entities.User;
import com.jwt.springSecurity.repository.UserRepository;
import com.jwt.springSecurity.services.AuthenticationService;
import com.jwt.springSecurity.services.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private final JWTService jwtService;
	
	public User signup(SignUpRequest signUpRequest) {
		User user = new User();
		
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.USER);
		return userRepository.save(user);	
	}
	
	public JwtAuthenticationResponse signin(SigninRequest signInRequest) {
		authenticationManager.authenticate
		(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), 
				signInRequest.getPassword()));
		// check var
		var user = userRepository.findByEmail(signInRequest.getEmail()).
				orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
		String jwt = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse;
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			String jwt = jwtService.generateToken(user);
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
		}
		return null;
	}
}
