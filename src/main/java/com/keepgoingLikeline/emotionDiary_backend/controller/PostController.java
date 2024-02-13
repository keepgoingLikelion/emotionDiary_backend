package com.keepgoingLikeline.emotionDiary_backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostsDto;
import com.keepgoingLikeline.emotionDiary_backend.service.PostService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/post")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 기록(post) 생성
     * 
     * request body(postUploadDto)ㄱ
     * emtionType: int (required)
     * content: string (required)
     * 
     * @param postUploadDto post에 담길 내용 -> {emotionType, content}
     * @return http state code
     */
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostUploadDto postUploadDto) {
        if (postUploadDto.getEmotionType() == null || postUploadDto.getContent() == null) {
            String msg = "Post creation failed: ";
            msg += postUploadDto.getEmotionType() == null ? "emotionType " : "";
            msg += postUploadDto.getContent() == null ? "content " : "";
            return new ResponseEntity<>(msg + "is required.", HttpStatus.BAD_REQUEST);
        }

        return postService.createPost(postUploadDto);
    }
    
    /**
     * 기록 조회
     * - postId로 -
     * 
     * postId만 파라미터로 받아 기록을 조회함
     * 
     * @param postId
     * @return postDto
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId) {
        PostDto post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    /**
     * 기록 리스트 조회
     * - main page 용 -
     * 
     * 현재 날짜로 저장되고 category list의 목록에 들어간 emotionTypes에 해당하는 기록(post)를 리턴합니다.
     * req -> { post: PostSimpleDto[] }
     * 
     * @param category 조회할 기록의 emotionTypes
     * @param howMany 리턴할 postList 길이
     * @param pageNum howMany가 리스트 길이 일 때, 쪽 수
     * @return { posts: PostSimpleDto[] }
     */
    @GetMapping("/postList")
    public ResponseEntity<PostsDto> getPostList(
            @RequestParam("category") String category,
            @RequestParam(value = "howMany", required = false) Integer howMany,
            @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        // 요청 확인 및 포맷팅
        if(howMany==null ^ pageNum==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Integer> cate = new ArrayList<>();
        String[] tokens = category.split(",");
        try{
            for(String i: tokens){
                cate.add(Integer.parseInt(i));
            }
        } catch(Exception e){
            System.out.println(">>> can't get the emotionTypes\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // get posts
        PostsDto response = postService.getPostList(cate, howMany, pageNum);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 기록 리스트 조회
     * - mypage 캘린더 용 -
     * 
     * @param year
     * @param month
     * @return { posts: PostSimpleDto[] }
     */
    @GetMapping("/mypage")
    public ResponseEntity<PostsDto> getMyposts(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        if(month>12 || month <=0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PostsDto posts = postService.getmyposts(year, month);
        if(posts==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }

    }

    /**
     * 사용자의 오늘 기록 조회
     * 
     * 요청을 보낸 사용자의 오늘 기록을 조회해 리턴합니다
     * 사용자 정보 없음 -> 401
     * 오늘 날짜의 post가 존재하지 않음 -> 404
     * 
     * @return PostSimpleDto
     */
    @GetMapping("/myPost")
    public ResponseEntity<PostDto> getMyPost(){
        return postService.getMyPost();
    }

    /**
     * 기록 리스트 조회
     * - 그땐 머랭 용 -
     * 
     * 요청을 보낸 사용자가 이모지를 단 글을 emotionType만 필터링해서 리턴
     * 사용자 정보 없음 -> 401
     */
    @GetMapping("/myLikePostList")
    public ResponseEntity<PostsDto> getLikePostList(@RequestParam("emotionType") Integer emotionType){
        PostsDto posts = postService.getLikePostList(emotionType);
        if(posts==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }

    /**
     * 사용자가 이모지를 단 포스트를 emotionType별로 카운트 해 배열로 리턴함
     * 
     * 사용자 정보 없음 -> 401
     * @return List<Long>
     */
    @GetMapping("/myLikeCountList")
    public ResponseEntity<List<Long>> getLikeCountList() {
        List<Long> countList = postService.getLikeCountList();
        if(countList==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            return new ResponseEntity<>(countList, HttpStatus.OK);
        }
    }
    

    /**
     * 기록 삭제
     * 
     * postId를 파라미터로 받아 기록을 삭제함
     * @param postId
     * @return http status code
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId) {
        ResponseEntity<String> response = postService.deletePost(postId);
        return response;
    }

    /**
     * 기록 수정
     * 
     * postId를 파라미터로, 수정내용을 req body로 받아 기록을 수정함
     * 
     * request body(postUploadDto)ㄱ
     * emtionType: int (required)
     * content: string (required)
     * 
     * @param postId
     * @param postUploadDto post에 담길 내용 -> {emotionType, content}
     * @return http status code
     */
    @PutMapping("/{postId}")
    public ResponseEntity<String> putPost(@PathVariable("postId") Long postId, @RequestBody PostUploadDto postUploadDto) {
        ResponseEntity<String> response = postService.putPost(postId, postUploadDto);
        return response;
    }
}
