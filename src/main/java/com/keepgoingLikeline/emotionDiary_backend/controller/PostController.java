package com.keepgoingLikeline.emotionDiary_backend.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.PostDto;
import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.service.PostService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/post")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")   //TODO 한번에 설정하는 방법 없음?
public class PostController {
    @Autowired
    PostService postService;

    // TODO 유저확인은 전체적으로 Controller에서 하기(적절한 http status를 던져주기 위함)

    /**
     * 기록(post) 생성
     * 
     * request body(postUploadDto)ㄱ
     * emtionType: int (required)
     * content: string (required)
     * 
     * @param postUploadDto
     * @return http state code
     */
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostUploadDto postUploadDto){
        // request body check
        if(postUploadDto.getEmotionType()==null || postUploadDto.getContent()==null){
            String msg = "post fail: ";
            msg += postUploadDto.getEmotionType()==null ? "emotionType " : "";
            msg += postUploadDto.getContent()==null ? "content " : "";
            return new ResponseEntity<>(msg + "is null", HttpStatus.BAD_REQUEST);
        } else{
            // try post save
            if (postService.createPost(postUploadDto)){
                return new ResponseEntity<>("post success", HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>("post fail", HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    /**
     * 기록 조회
     * - postId로 -
     * 
     * postId만 파라미터로 받아 기록을 조회함
     * 
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        // TODO 유저로그인 확인 절차 필요
        PostDto post = postService.getPostById(postId);
        if(post==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    /**
     * 기록 삭제
     * 
     * postId를 파라미터로 받아 기록을 삭제함
     * @param postId
     * @return
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        // TODO 유저 본인 확인 절차 필요
        boolean isSccess = postService.deletePost(postId);
        if(isSccess){
            return new ResponseEntity<>("delete sccess", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Post not found with id " + postId, HttpStatus.NOT_FOUND);
        }
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
     * @param postUploadDto
     * @return
     */
    @PutMapping("/{postId}")
    public ResponseEntity<String> putPost(@PathVariable Long postId, @RequestBody PostUploadDto postUploadDto){
        ResponseEntity<String> response = postService.putPost(postId, postUploadDto);
        return response;
    }
}
