package com.keepgoingLikeline.emotionDiary_backend.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {
	private Long postId;
    private Long userId;
    private LocalDate createdDate;
    private String username;
    private Integer emotionType;
    private String content;
    private List<EmojiInfoResponseDto> emojis;
}
