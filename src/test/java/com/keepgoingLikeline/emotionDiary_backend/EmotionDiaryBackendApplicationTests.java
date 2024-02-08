package com.keepgoingLikeline.emotionDiary_backend;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;
import com.keepgoingLikeline.emotionDiary_backend.repository.UserRepository;

@SpringBootTest
class EmotionDiaryBackendApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
		UserEntity user = userRepository.findById(1L).orElse(null);
		PostEntity post = postRepository.findByUserAndCreatedDate(user, LocalDate.now());

		try{
			throw new Exception("======================\n"+post.getContent());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
