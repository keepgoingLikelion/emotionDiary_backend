package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.CreateAccessTokenRequest;
import com.keepgoingLikeline.emotionDiary_backend.dto.CreateAccessTokenResponse;
import com.keepgoingLikeline.emotionDiary_backend.service.TokenService;

@RequestMapping("/api")
@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/token")
	public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
			@RequestBody CreateAccessTokenRequest request){
		
		String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
	}
}
