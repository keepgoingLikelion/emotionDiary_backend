package com.keepgoingLikeline.emotionDiary_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmojiClickInfoRequest {
	
	private double x;
    private double y;
    
    private String emojiUrl;
    
    private Long postId;
}
