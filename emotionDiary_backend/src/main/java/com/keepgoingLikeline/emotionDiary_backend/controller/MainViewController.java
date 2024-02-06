package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainViewController {
	
	@GetMapping("/main")
	public String mainView() {
		return "main";
	}
}
