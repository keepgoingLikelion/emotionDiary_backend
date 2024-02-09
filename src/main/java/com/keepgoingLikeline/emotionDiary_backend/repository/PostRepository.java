package com.keepgoingLikeline.emotionDiary_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    
    List<PostEntity> findByCreatedDateAndEmojiTypeIn(
        LocalDate createdDate,
        List<Integer> EmojiTypes
    );
    Page<PostEntity> findByCreatedDateAndEmojiTypeIn(
        LocalDate createdDate,
        List<Integer> EmojiTypes,
        Pageable pageable
    );

    List<PostEntity> findByUserAndCreatedDateBetween(
        UserEntity user,
        LocalDate from,
        LocalDate to
    );

    PostEntity findByUserAndCreatedDate(
        UserEntity user,
        LocalDate creatDate
    );

    List<PostEntity> findByEmojiTypeAndUser(
        Integer emojiType,
        UserEntity user
    );

    Long countByEmojiTypeAndUser(
        Integer emojiType,
        UserEntity user
    );
}
