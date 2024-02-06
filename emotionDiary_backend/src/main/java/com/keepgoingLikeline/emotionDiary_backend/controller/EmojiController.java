package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiClickInfoRequest;
import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiUrlResponse;
import com.keepgoingLikeline.emotionDiary_backend.service.EmojiService;

@RequestMapping("/api")
@RestController
public class EmojiController {
	
	@Autowired
	private EmojiService emotionService;
	
	@GetMapping("/emoji")
    public ResponseEntity<EmojiUrlResponse> getEmojis() {
		
        String[] emojiUrls = emotionService.getEmotionUrls();
        EmojiUrlResponse response = new EmojiUrlResponse(emojiUrls);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/saveEmoji")
    public ResponseEntity<Void> saveImageClicks(@RequestBody EmojiClickInfoRequest request) {
		emotionService.saveEmoji(request);
		
        return ResponseEntity.ok().build();
    }
}
