package com.keepgoingLikeline.emotionDiary_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {
	private Long postId;
    private Long userId;
    private String username;
    private Integer emotionType;
    private String content;
    private List<CommentDto> comments;
}
