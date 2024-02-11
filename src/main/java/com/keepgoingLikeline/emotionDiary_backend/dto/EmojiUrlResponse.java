package com.keepgoingLikeline.emotionDiary_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmojiUrlResponse {
	
	String[] emojiTypeUrls;
	String[][] emojiUrls;
}
