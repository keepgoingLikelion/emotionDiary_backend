package com.keepgoingLikeline.emotionDiary_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(Long userId);
}
