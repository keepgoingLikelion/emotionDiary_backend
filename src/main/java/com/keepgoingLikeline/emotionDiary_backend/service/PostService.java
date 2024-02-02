package com.keepgoingLikeline.emotionDiary_backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.keepgoingLikeline.emotionDiary_backend.dto.CommentDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostSimpleDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostsDto;
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
    public PostDto getPostById(Long postId){
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
     * 기록 List 조회 서비스
     * - main page 용 -
     * 
     * @param category
     * @param howMany
     * @param pageNum
     * @return
     */
    public PostsDto getPostList(List<Integer> category, Integer howMany, Integer pageNum){
        List<PostEntity> postEntities = new ArrayList<>();

        if(howMany==null){
            postEntities = postRepository.findByCreatedDateAndEmotionTypeIn(LocalDate.now(), category);
        } else{
            Pageable pageable = PageRequest.of(pageNum, howMany);
            postEntities = postRepository.findByCreatedDateAndEmotionTypeIn(
                    LocalDate.now(),
                    category,
                    pageable
                ).getContent();
        }

        return convertPostEntities2PostsDto(postEntities);
    }

    /**
     * 기록 삭제 서비스
     * @param postId
     * @return
     */
    public boolean deletePost(Long postId){
        // post 조회
        PostEntity post = postRepository.findByPostId(postId);
        try{
            if(post==null){
                throw new Exception(">>> Post not found with id: "+postId);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        // 삭제
        postRepository.delete(post);
        return true;
    }

    /**
     * 기록 수정 서비스
     * 
     * @param postId
     * @param postUploadDto
     * @return
     */
    public ResponseEntity<String> putPost(Long postId, PostUploadDto postUploadDto){
        // TODO user 조회
        long userId = 123123L;
        UserEntity user = userRepository.findByUserId(userId);
        try{
            if(user==null){
                throw new Exception(">>> User not found with id: "+userId);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("user not found with id: "+userId, HttpStatus.UNAUTHORIZED);
        }
        // 게시물 postId 확인
        ResponseEntity<PostEntity> postRes = checkPost(postId, user);
        if(postRes.getBody()==null){
            return new ResponseEntity<>(
                postRes.getStatusCode()==HttpStatus.NOT_FOUND ? "Post not found with id: "+postId : "user "+userId+" does not have permission",
                postRes.getStatusCode()
            );
        }
        // 수정요청시간 확인
        PostEntity post = postRes.getBody();
        if(!post.getCreatedDate().equals(LocalDate.now())){
            return new ResponseEntity<>("modify is not allowed", HttpStatus.BAD_REQUEST);
        }
        
        //수정
        if(postUploadDto.getEmotionType()!=null){
            post.setEmotionType(postUploadDto.getEmotionType());
        }
        if(postUploadDto.getContent()!=null){
            post.setContent(postUploadDto.getContent());
        }
        postRepository.save(post);
        return new ResponseEntity<>("put(fetch) sccess", HttpStatus.CREATED);
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
        postDto.setCreatedDate(postEntity.getCreatedDate());
        postDto.setEmotionType(postEntity.getEmotionType());
        postDto.setContent(postEntity.getContent());
        postDto.setComments(comments);

        return postDto;
    }

    /**
     * PostEntity List -> PostsDto 내장 함수
     * 
     * @param postEntities
     * @return
     */
    private PostsDto convertPostEntities2PostsDto(List<PostEntity> postEntities){
        List<PostSimpleDto> posts = new ArrayList<>();

        Iterator<PostEntity> iter = postEntities.iterator();
        while(iter.hasNext()){
            PostSimpleDto postSimpleDto = new PostSimpleDto();
            PostEntity postEntity = iter.next();
            postSimpleDto.setPostId(postEntity.getPostId());
            postSimpleDto.setUserId(postEntity.getUser().getUserId());
            postSimpleDto.setUsername(postEntity.getUser().getUsername());
            postSimpleDto.setCreatedDate(postEntity.getCreatedDate());
            postSimpleDto.setEmotionType(postEntity.getEmotionType());
            postSimpleDto.setContent(postEntity.getContent());
            posts.add(postSimpleDto);
        }

        return new PostsDto(posts);
    }

    /**
     * postId를 통해 post 사용가능 여부를 체크하는 함수.
     * 
     * @param postId
     * @param user userEntity. null이 아니라면 주어진 포스트의 userId와 비교해 권한 체크를 한다.
     * @return
     */
    private ResponseEntity<PostEntity> checkPost(Long postId, UserEntity user){
        // post가 존재하지 않는 경우 404
        PostEntity post = postRepository.findByPostId(postId);
        if(post==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // + 유저가 주어진 경우, post의 권한을 가지고 있지 않다면 403
        if(user!=null){
            if(user.getUserId()!=post.getUser().getUserId())
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
