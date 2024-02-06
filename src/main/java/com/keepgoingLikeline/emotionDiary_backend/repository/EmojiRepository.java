package com.keepgoingLikeline.emotionDiary_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keepgoingLikeline.emotionDiary_backend.entity.EmojiEntity;

@Repository
public interface EmojiRepository extends JpaRepository<EmojiEntity, Long>{

}
