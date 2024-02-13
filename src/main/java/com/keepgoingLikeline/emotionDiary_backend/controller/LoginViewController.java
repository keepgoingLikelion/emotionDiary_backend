package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class LoginViewController {
	
	@GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }
}
