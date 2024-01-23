package com.keepgoingLikeline.emotionDiary_backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.dto.CommentDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.entity.CommentEntity;
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

        // user 조회
        long userId = 123123L;
        UserEntity user = userRepository.findByUserId(userId);
        try{
            if(user==null){
                throw new Exception(">>> User not found with id: "+userId);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        // postUploadDto -> postEntity
        PostEntity newPost = new PostEntity();
        newPost.setUser(user);
        newPost.setEmotionType(postUploadDto.getEmotionType());
        newPost.setContent(postUploadDto.getContent());
        newPost.setCreatedDate(LocalDate.now());
        
        postRepository.save(newPost);
        return true;
    }

    /**
     * 기록 조회 서비스
     * - postId로 -
     * 
     * @param postId
     * @return
     */
    public PostDto getPostById(long postId){
        // post 조회
        PostEntity post = postRepository.findByPostId(postId);
        try{
            if(post==null){
                throw new Exception(">>> Post not found with id: "+postId);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        return convertPostEntity2PostDto(post);
    }

    /**
     * postEntity -> postDto 내장 함수
     * 
     * @param postEntity
     * @return
     */
    private PostDto convertPostEntity2PostDto(PostEntity postEntity){
        // commentEntity -> commentDto = comments
        List<CommentDto> comments = new ArrayList<>();
        List<CommentEntity> commentsEntities = postEntity.getComments();
        Iterator<CommentEntity> iter = commentsEntities.iterator();
        while(iter.hasNext()){
            CommentDto commentDto = new CommentDto();
            CommentEntity commentEntity = iter.next();
            commentDto.setCommentId(commentEntity.getCommentId());
            commentDto.setContent(commentEntity.getContent());
            comments.add(commentDto);
        }
        
        // postEntity -> postDto
        PostDto postDto = new PostDto();
        postDto.setPostId(postEntity.getPostId());
        postDto.setUserId(postEntity.getUser().getUserId());
        postDto.setUsername(postEntity.getUser().getUsername());
        postDto.setEmotionType(postEntity.getEmotionType());
        postDto.setContent(postEntity.getContent());
        postDto.setComments(comments);

        return postDto;
    }
}
