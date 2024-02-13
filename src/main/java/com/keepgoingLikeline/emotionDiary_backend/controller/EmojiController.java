package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiClickInfoRequest;
import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiInfoResponseDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiUrlResponse;
import com.keepgoingLikeline.emotionDiary_backend.service.EmojiService;

@RequestMapping("/api")
@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class EmojiController {
	
	@Autowired
	private EmojiService emojiService;
	
	@GetMapping("/emoji")
    public ResponseEntity<EmojiUrlResponse> getEmojis() {
		
		String[] emojiTypeUrls = emojiService.getEmojiTypeUrls();
        String[][] emojiUrls = emojiService.getEmojiUrls();
        EmojiUrlResponse response = new EmojiUrlResponse(emojiTypeUrls, emojiUrls);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/saveEmoji")
    public ResponseEntity<EmojiInfoResponseDto> saveImageClicks(@RequestBody EmojiClickInfoRequest request) {
		
		EmojiInfoResponseDto response = EmojiInfoResponseDto.toDto(emojiService.saveEmoji(request));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
