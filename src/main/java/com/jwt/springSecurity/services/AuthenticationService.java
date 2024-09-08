package com.jwt.springSecurity.services;

import org.springframework.stereotype.Service;

import com.jwt.springSecurity.dto.JwtAuthenticationResponse;
import com.jwt.springSecurity.dto.RefreshTokenRequest;
import com.jwt.springSecurity.dto.SigninRequest;
import com.jwt.springSecurity.dto.SignUpRequest;
import com.jwt.springSecurity.entities.User;

//@Service
public interface AuthenticationService {

	User signup(SignUpRequest signUpRequest);
	JwtAuthenticationResponse signin(SigninRequest signInRequest);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
