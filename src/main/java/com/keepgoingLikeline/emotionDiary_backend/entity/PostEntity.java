package com.keepgoingLikeline.emotionDiary_backend.entity;

import java.util.ArrayList;
import java.util.List;

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

    private Integer emotionType;

    private String content;

	@OneToMany
    @JoinColumn(name="comments")
	private List<CommentEntity> comments = new ArrayList<>();
}