package com.simplyfly.services;

import com.simplyfly.dto.JWTAuthResponse;
import com.simplyfly.dto.LoginDto;
import com.simplyfly.dto.RegisterDto;

public interface AuthService {

	JWTAuthResponse login(LoginDto dto);

	String register(RegisterDto dto);

}