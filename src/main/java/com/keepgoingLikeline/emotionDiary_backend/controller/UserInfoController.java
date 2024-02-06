package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;

@RestController
@RequestMapping("/api")
public class UserInfoController {

	@GetMapping("/user/me")
    public ResponseEntity<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).body(null);
        }

        String email = "";
        Object principal = authentication.getPrincipal();

        UserEntity userInfo = new UserEntity();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
            userInfo.setEmail(email);
        } else if (principal instanceof String) {

            email = (String) principal;
            userInfo.setEmail(email);
        }

        return ResponseEntity.ok(userInfo);
    }
}