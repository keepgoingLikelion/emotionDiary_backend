package com.keepgoingLikeline.emotionDiary_backend.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.config.jwt.TokenProvider;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;

@Service
public class TokenService {
	
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private UserService userService;
	
	public String createNewAccessToken(String refreshToken) {
		
		// 토큰 유효성 검사에 실패하면 예외 발생
		if(!tokenProvider.validToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}
		
		Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
		UserEntity user = userService.findById(userId);
		
		return tokenProvider.generateToken(user, Duration.ofHours(2));
	}
}
