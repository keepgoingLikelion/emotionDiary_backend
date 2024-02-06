package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.dto.EmojiClickInfoRequest;
import com.keepgoingLikeline.emotionDiary_backend.entity.EmojiEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.EmojiRepository;

@Service
public class EmojiService {
	
	@Autowired
	private EmojiRepository emojiRepository;
	
	public String[] getEmotionUrls() {
        return new String[]{
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type.png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(1).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(2).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(3).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(4).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(5).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(6).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(7).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(8).png",
            "https://emotiondiary-bucket.s3.ap-northeast-2.amazonaws.com/%ED%91%9C%EC%A4%80type+(9).png"
        };
    }
	
	public void saveEmoji(EmojiClickInfoRequest request) {
        EmojiEntity emojiEntity = new EmojiEntity();
            
        emojiEntity.setX(request.getX());
        emojiEntity.setY(request.getY());
        emojiEntity.setEmojiIndex(request.getEmojiIndex());
            
        emojiRepository.save(emojiEntity); // 이미지 클릭 정보를 데이터베이스에 저장
    }
}
