package com.keepgoingLikeline.emotionDiary_backend;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;

@SpringBootTest
class EmotionDiaryBackendApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Test
	void contextLoads() {
	}

}
