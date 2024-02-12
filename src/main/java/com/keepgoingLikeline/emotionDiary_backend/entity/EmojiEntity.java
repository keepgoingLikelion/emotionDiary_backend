package com.keepgoingLikeline.emotionDiary_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Emoji")
@Entity
public class EmojiEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private double x;
    private double y;
    private String emojiUrl;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity post;
}
