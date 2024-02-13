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

import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostSimpleDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostsDto;
import com.keepgoingLikeline.emotionDiary_backend.entity.PostEntity;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    // post controller ----------------------------

    /**
     * 포스트 업로드 서비스
     * postUploadDto(emotionType, content)를 받아 새 게시물 저장
     * @param postUploadDto
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> createPost(PostUploadDto postUploadDto){
        // 현재 로그인된 사용자 정보 가져오기
        UserEntity user = userService.getUserEntity();
        if(user == null){
            return new ResponseEntity<>("Post creation failed due to authorization issues.", HttpStatus.UNAUTHORIZED);
        }

        if(postRepository.findByUserAndCreatedDate(user, LocalDate.now())!=null){
            return new ResponseEntity<>("already exist the today's post written by user", HttpStatus.CONFLICT);
        }

        // postUploadDto -> postEntity
        PostEntity newPost = new PostEntity();
        newPost.setUser(user);
        newPost.setEmotionType(postUploadDto.getEmotionType());
        newPost.setContent(postUploadDto.getContent());
        newPost.setCreatedDate(LocalDate.now());
        
        postRepository.save(newPost);
        return new ResponseEntity<>("Post created successfully.", HttpStatus.CREATED);
    }

    // get controller ----------------------------------

    /**
     * 기록 조회 서비스
     * - postId로 -
     * 
     * @param postId
     * @return
     */
    public PostDto getPostById(Long postId){
        PostEntity post = postRepository.findByPostId(postId);
        if(post == null){
            return null;
        }

        PostDto postDto = post.toPostDto();
        return postDto;
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
     * 기록 List 조회 서비스
     * - mypage 캘린더 용 -
     * 
     * @param year
     * @param month
     * @return
     */
    public PostsDto getmyposts(int year, int month){
        UserEntity user = userService.getUserEntity();
        if(user == null){
            return null;
        }
        
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = month == 12 ? LocalDate.of(year + 1, 1, 1) : LocalDate.of(year, month + 1, 1);

        List<PostEntity> postEntities = postRepository.findByUserAndCreatedDateBetween(user, from, to.minusDays(1));

        return convertPostEntities2PostsDto(postEntities);
    }

    /**
     * 사용자의 오늘 기록 조회 서비스
     * 
     * 사용자 정보 없음 -> 401
     * 오늘 날짜의 post가 존재하지 않음 -> 404
     * 
     * @return
     */
    public ResponseEntity<PostDto> getMyPost(){
        UserEntity user = userService.getUserEntity();
        if(user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        PostEntity post = postRepository.findByUserAndCreatedDate(user, LocalDate.now());
        if(post==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(post.toPostDto(), HttpStatus.OK);
    }

    /**
     * 기록 List 조회 서비스
     * - 그땐 머랭 용 -
     * 
     * @param emotionType
     * @return
     */
    public PostsDto getLikePostList(Integer emotionType){
        UserEntity user = userService.getUserEntity();
        if(user == null){
            return null;
        }

        List<PostEntity> postEntities = postRepository.findByEmotionTypeAndEmojis_User(emotionType, user);

        return convertPostEntities2PostsDto(postEntities);
    }

    /**
     * 그때 머랭 카운팅 서비스
     * 
     * @return
     */
    public List<Long> getLikeCountList(){
        UserEntity user = userService.getUserEntity();
        if(user == null){
            return null;
        }

        List<Long> countList = new ArrayList<>();
        for(int emotionType=1; emotionType<7; emotionType++){
            countList.add(postRepository.countByEmotionTypeAndEmojis_User(emotionType, user));
        }
        return countList;
    }

    // del controller ---------------------------

    /**
     * 기록 삭제 서비스
     * @param postId
     * @return
     */
    public ResponseEntity<String> deletePost(Long postId){
        UserEntity user = userService.getUserEntity();
        try{
            if(user==null){
                throw new Exception(">>> User not found");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("user not found", HttpStatus.UNAUTHORIZED);
        }
        // 게시물 postId 확인
        ResponseEntity<PostEntity> postRes = checkPost(postId, user);
        if(postRes.getBody()==null){
            return new ResponseEntity<>(
                postRes.getStatusCode()==HttpStatus.NOT_FOUND ? "Post not found with id: "+postId : "user does not have permission",
                postRes.getStatusCode()
            );
        }

        // 삭제
        postRepository.delete(postRes.getBody());
        return new ResponseEntity<>("delete sccess", HttpStatus.OK);
    }

    // put controller ---------------------------

    /**
     * 기록 수정 서비스
     * 
     * @param postId
     * @param postUploadDto
     * @return
     */
    public ResponseEntity<String> putPost(Long postId, PostUploadDto postUploadDto){
        UserEntity user = userService.getUserEntity();
        try{
            if(user==null){
                throw new Exception(">>> User not found");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("user not found", HttpStatus.UNAUTHORIZED);
        }
        // 게시물 postId 확인
        ResponseEntity<PostEntity> postRes = checkPost(postId, user);
        if(postRes.getBody()==null){
            return new ResponseEntity<>(
                postRes.getStatusCode()==HttpStatus.NOT_FOUND ? "Post not found with id: "+postId : "user does not have permission",
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

    // private method ---------------------------------

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
            posts.add(iter.next().toPostSimpleDto());
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
            if(user.getId()!=post.getUser().getId())
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
