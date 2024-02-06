package com.keepgoingLikeline.emotionDiary_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateAccessTokenResponse {
	
    private String accessToken;
}

