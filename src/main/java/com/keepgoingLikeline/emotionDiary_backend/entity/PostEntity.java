package com.keepgoingLikeline.emotionDiary_backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiInfoResponseDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostSimpleDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "post_table")
public class PostEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

	@ManyToOne
    @JoinColumn(name="userId")
	private UserEntity user;

    @Column
    @CreatedDate
    private LocalDate createdDate;

    private Integer emotionType;

    private String content;
    
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="postId")
    private List<EmojiEntity> emojis = new ArrayList<>();

    /**
     * postEntity -> postDto
     * 
     * @param postEntity
     * @return
     */
    public PostDto toPostDto() {
        PostDto postDto = new PostDto();
        postDto.setPostId(getPostId());
        postDto.setUserId(getUser().getId());
        postDto.setUsername(getUser().getUsername());
        postDto.setCreatedDate(getCreatedDate());
        postDto.setEmotionType(getEmotionType());
        postDto.setContent(getContent());

        // EmojiEntity 리스트를 EmojiInfoResponseDto 리스트로 변환
        List<EmojiInfoResponseDto> emojiInfoResponseDtos = this.emojis.stream()
                .map(emoji -> new EmojiInfoResponseDto(emoji.getId(), emoji.getX(), emoji.getY(), emoji.getEmojiUrl()))
                .collect(Collectors.toList());

        postDto.setEmojis(emojiInfoResponseDtos);

        return postDto;
    }
    /**
     * postEntity -> postSimpleDto
     * 
     * @return postSimpleDto
     */
    public PostSimpleDto toPostSimpleDto() {
        PostSimpleDto postSimpleDto = new PostSimpleDto();
        postSimpleDto.setPostId(getPostId());
        postSimpleDto.setUserId(getUser().getId());
        postSimpleDto.setUsername(getUser().getUsername());
        postSimpleDto.setCreatedDate(getCreatedDate());
        postSimpleDto.setEmotionType(getEmotionType());
        postSimpleDto.setContent(getContent());
        postSimpleDto.setEmojiCount(getEmojis().size());

        return postSimpleDto;
    }
}