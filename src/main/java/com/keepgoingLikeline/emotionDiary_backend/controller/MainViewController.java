package com.keepgoingLikeline.emotionDiary_backend.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;
import com.keepgoingLikeline.emotionDiary_backend.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class MainViewController {

	@Autowired
	PostRepository postRepository;

	@Autowired
    private UserService userService;
	
	@GetMapping("/main")
	public void mainView(HttpServletResponse httpServletResponse) {
		UserEntity user = userService.getUserEntity();
		if(user==null){
			httpServletResponse.setHeader("Location", "http://localhost:8080/login");
		}
		if(postRepository.findByUserAndCreatedDate(user, LocalDate.now())==null){
			httpServletResponse.setHeader("Location", "http://localhost:5173/newPost");
		} else{
			httpServletResponse.setHeader("Location", "http://localhost:5173/main");
		}

		httpServletResponse.setStatus(302);
	}
}
