package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;
import com.keepgoingLikeline.emotionDiary_backend.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 포스트 업로드 서비스
     * postUploadDto(emotionType, content)를 받아 새 게시물 저장
     * @param postUploadDto
     * @return 성공?
     */
    public boolean createPost(PostUploadDto postUploadDto){
        //TODO 로그인 확인 및 userEntity 받아 저장하기
        UserEntity user = userRepository.findByUserId(123123L);

        PostEntity newPost = new PostEntity();
        newPost.setUser(user);
        newPost.setEmotionType(postUploadDto.getEmotionType());
        newPost.setContent(postUploadDto.getContent());
        
        postRepository.save(newPost);
        return true;
    }
}
