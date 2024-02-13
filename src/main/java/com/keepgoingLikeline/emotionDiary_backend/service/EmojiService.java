package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiClickInfoRequest;
import com.keepgoingLikeline.emotionDiary_backend.entity.EmojiEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.EmojiRepository;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;
import com.keepgoingLikeline.emotionDiary_backend.repository.UserRepository;

@Service
public class EmojiService {
	
	@Autowired
	private EmojiRepository emojiRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	
	public String[][] getEmojiUrls() {
		return new String[][]{
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg"},
	        
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        		"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg"},
	        
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg"},
	        
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        				"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg"},
	        
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        					"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg"},

	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg",
	        						"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg"}
	    };
    }
	
	public String[] getEmojiTypeUrls() {
		
		return new String[] {
			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/HappinessType.svg",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SadnessType.svg",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/AngerType.svg",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/SensitiveType.svg",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/ConcernType.svg",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/TiredType.svg"
		};
	}
	
	public EmojiEntity saveEmoji(EmojiClickInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + request.getPostId()));

        EmojiEntity emojiEntity = new EmojiEntity();
        emojiEntity.setX(request.getX());
        emojiEntity.setY(request.getY());
        emojiEntity.setEmojiUrl(request.getEmojiUrl());
        emojiEntity.setUser(user);
        emojiEntity.setPost(post);

        return emojiRepository.save(emojiEntity);
    }
}
