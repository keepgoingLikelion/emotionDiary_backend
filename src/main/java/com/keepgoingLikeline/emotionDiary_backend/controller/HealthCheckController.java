package com.keepgoingLikeline.emotionDiary_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
	private String env;
	
	@GetMapping("/hc")
	public ResponseEntity<?> healthCheck(){
		Map<String, String> responseDate = new HashMap<>();
		
		return ResponseEntity.ok(responseDate);
	}
	
	@GetMapping("/env")
	public ResponseEntity<?> getEnv(){
		
		return ResponseEntity.ok(env);
	}
}
