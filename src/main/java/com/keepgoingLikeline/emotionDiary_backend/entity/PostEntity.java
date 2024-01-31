package com.keepgoingLikeline.emotionDiary_backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

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
	private List<CommentEntity> comments = new ArrayList<>();
}