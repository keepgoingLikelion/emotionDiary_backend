package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.keepgoingLikeline.emotionDiary_backend.entity.GoogleUser;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class GoogleUserInfoController {
	
	@GetMapping("/googleUserInfo")
    public GoogleUser getGoogleUserInfo(@RequestParam(name="accessToken") String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<GoogleUser> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo", 
                HttpMethod.GET, 
                entity, 
                GoogleUser.class);

        return response.getBody();
    }
}