package com.keepgoingLikeline.emotionDiary_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmojiInfoResponseDto {
	private Long commentId;
	private double x;
    private double y;
    private Long emojiIndex;
}
