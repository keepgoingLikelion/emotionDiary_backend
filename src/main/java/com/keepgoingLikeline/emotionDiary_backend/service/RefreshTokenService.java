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
	
	public RefreshToken createOrUpdateRefreshToken(Long userId, String refreshTokenString) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(userId)
            .map(existingToken -> existingToken.update(refreshTokenString))
            .orElse(new RefreshToken(userId, refreshTokenString));
        
        return refreshTokenRepository.save(refreshTokenEntity);
    }
}
