package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.UpdateNicknameRequest;
import com.keepgoingLikeline.emotionDiary_backend.dto.UserInfoResponse;
import com.keepgoingLikeline.emotionDiary_backend.service.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user/me")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
		
		UserInfoResponse userInfo = userService.getUserInfo();
        if (userInfo == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(userInfo);
    }
	
	@PostMapping("/updateNickname")
    public ResponseEntity<String> updateNickname(@RequestBody UpdateNicknameRequest request) {
        if (userService.updateUserNickname(request.getNewNickname())) {
            return ResponseEntity.ok("Nickname updated success");
        }
        else {
            return ResponseEntity.badRequest().body("Failed to update nickname.");
        }
    }
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }
}
