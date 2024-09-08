package com.jwt.springSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.springSecurity.dto.JwtAuthenticationResponse;
import com.jwt.springSecurity.dto.RefreshTokenRequest;
import com.jwt.springSecurity.dto.SigninRequest;
import com.jwt.springSecurity.dto.SignUpRequest;
import com.jwt.springSecurity.entities.User;
import com.jwt.springSecurity.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signup(signUpRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signInRequest){
		return ResponseEntity.ok(authenticationService.signin(signInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}

}
