package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.entity.RefreshToken;
import com.keepgoingLikeline.emotionDiary_backend.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken findByRefreshToken(String refreshToken) {
		
		return refreshTokenRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new IllegalArgumentException("Unexpecte token"));
	}
}
