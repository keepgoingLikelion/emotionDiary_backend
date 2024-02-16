package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiClickInfoRequest;
import com.keepgoingLikeline.emotionDiary_backend.entity.EmojiEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.EmojiRepository;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;

@Service
public class EmojiService {
	
	@Autowired
	private EmojiRepository emojiRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PostRepository postRepository;
	
	public String[][] getEmojiUrls() {
		return new String[][]{
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/cat1.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/cat2.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/cat3.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/cat4.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/cat5.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/sweet_potato.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/potato.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/carrot.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/french_fries.svg",
	        	"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/eggplant.svg"},
	        
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
        UserEntity user = userService.getUserEntity();

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + request.getPostId()));

        EmojiEntity emojiEntity = new EmojiEntity();
        emojiEntity.setX(request.getX());
        emojiEntity.setY(request.getY());
        emojiEntity.setEmojiUrl(request.getEmojiUrl());
        emojiEntity.setUser(user);
        emojiEntity.setPost(post);

        emojiRepository.save(emojiEntity);
		return emojiEntity;
    }
}
