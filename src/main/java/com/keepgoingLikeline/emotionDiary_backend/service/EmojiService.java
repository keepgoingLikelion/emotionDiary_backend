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
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type.png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(1).png"},
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(2).png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(3).png"},
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(4).png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(5).png"},
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(6).png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(7).png"},
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(8).png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(9).png"},
	        {"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(8).png",
	         "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(9).png"}
	    };
    }
	
	public String[] getEmojiTypeUrls() {
		
		return new String[] {
			"https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type.png",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(1).png",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(2).png",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(3).png",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(4).png",
	        "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(5).png",
		};
	}
	
	public void saveEmoji(EmojiClickInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + request.getPostId()));

        EmojiEntity emojiEntity = new EmojiEntity();
        emojiEntity.setX(request.getX());
        emojiEntity.setY(request.getY());
        emojiEntity.setEmojiLink(request.getEmojiLink());
        emojiEntity.setUser(user);
        emojiEntity.setPost(post);

        emojiRepository.save(emojiEntity);
    }
}
