package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.UpdateNicknameRequest;
import com.keepgoingLikeline.emotionDiary_backend.dto.UserInfoResponse;
import com.keepgoingLikeline.emotionDiary_backend.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user/me")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
		
		UserInfoResponse userInfoDto = userService.getUserInfo();
        if (userInfoDto == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(userInfoDto);
    }
	
	@PostMapping("/updateNickname")
    public ResponseEntity<String> updateNickname(@RequestBody UpdateNicknameRequest request) {
        if (userService.updateUserNickname(request.getNewNickname())) {
            return ResponseEntity.ok("Nickname updated success");
        } else {
            return ResponseEntity.badRequest().body("Failed to update nickname.");
        }
    }
}