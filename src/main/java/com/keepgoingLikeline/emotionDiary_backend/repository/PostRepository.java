package com.keepgoingLikeline.emotionDiary_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    
    List<PostEntity> findByCreatedDateAndEmotionTypeIn(
        LocalDate createdDate,
        List<Integer> EmotionTypes
    );
    Page<PostEntity> findByCreatedDateAndEmotionTypeIn(
        LocalDate createdDate,
        List<Integer> EmotionTypes,
        Pageable pageable
    );
}
