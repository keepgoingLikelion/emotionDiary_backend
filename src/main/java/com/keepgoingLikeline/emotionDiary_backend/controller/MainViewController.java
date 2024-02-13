package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MainViewController {
	
	@GetMapping("/main")
	public void mainView(HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Location", "http://localhost:5173/mypage");
		httpServletResponse.setStatus(302);
	}
}
