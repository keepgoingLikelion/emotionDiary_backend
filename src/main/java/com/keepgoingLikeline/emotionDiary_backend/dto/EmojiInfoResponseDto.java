package com.keepgoingLikeline.emotionDiary_backend.dto;

import com.keepgoingLikeline.emotionDiary_backend.entity.EmojiEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmojiInfoResponseDto {
	private Long id;
	private double x;
    private double y;
    private String emojiUrl;
    
    public static EmojiInfoResponseDto toDto(EmojiEntity requset) {
    	EmojiInfoResponseDto response = new EmojiInfoResponseDto();
    	
    	response.setId(requset.getId());
    	response.setX(requset.getX());
    	response.setY(requset.getY());
    	response.setEmojiUrl(requset.getEmojiUrl());
    	
    	return response;
    }
}
