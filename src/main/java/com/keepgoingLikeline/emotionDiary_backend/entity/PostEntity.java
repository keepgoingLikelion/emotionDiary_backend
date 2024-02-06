package com.keepgoingLikeline.emotionDiary_backend.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	@OneToMany(orphanRemoval = true)     // TODO 연관된 comment와 연결이 끊어질 때(보통 post 삭제 시) 자동으로 삭제를 해준다고 함. 테스트 필요
    @JoinColumn(name="postId")
	private List<EmojiEntity> comments = new ArrayList<>();

    /**
     * postEntity -> postDto
     * 
     * @param postEntity
     * @return
     */
    public PostDto toPostDto(){
        // EmojiEntity -> EmojiInfoResponseDto = comments
        List<EmojiInfoResponseDto> comments = new ArrayList<>();
        List<EmojiEntity> commentsEntities = getComments();
        Iterator<EmojiEntity> iter = commentsEntities.iterator();
        while(iter.hasNext()){
            EmojiInfoResponseDto emojiDto = new EmojiInfoResponseDto();
            EmojiEntity commentEntity = iter.next();
            emojiDto.setCommentId(commentEntity.getId());
            emojiDto.setX(commentEntity.getX());
            emojiDto.setY(commentEntity.getY());
            comments.add(emojiDto);
        }
        
        // postEntity -> postDto
        PostDto postDto = new PostDto();
        postDto.setPostId(getPostId());
        postDto.setUserId(getUser().getId());
        postDto.setUsername(getUser().getUsername());
        postDto.setCreatedDate(getCreatedDate());
        postDto.setEmotionType(getEmotionType());
        postDto.setContent(getContent());
        postDto.setComments(comments);

        return postDto;
    }
    
    /**
     * postEntity -> postSimpleDto
     * 
     * @return postSimpleDto
     */
    public PostSimpleDto toPostSimpleDto(){
        PostSimpleDto postSimpleDto = new PostSimpleDto();

        postSimpleDto.setPostId(getPostId());
        postSimpleDto.setUserId(getUser().getId());
        postSimpleDto.setUsername(getUser().getUsername());
        postSimpleDto.setCreatedDate(getCreatedDate());
        postSimpleDto.setEmotionType(getEmotionType());
        postSimpleDto.setContent(getContent());

        return postSimpleDto;
    }
}