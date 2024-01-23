package com.keepgoingLikeline.emotionDiary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoingLikeline.emotionDiary_backend.dto.PostUploadDto;
import com.keepgoingLikeline.emotionDiary_backend.service.PostService;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")   //TODO 한번에 설정하는 방법 없음?
public class PostController {
    @Autowired
    PostService postService;

    /**
     * 기록(post) 생성
     * 
     * request bodyㄱ
     * emtionType: int (required)
     * content: string (required)
     * 
     * @param postUploadDto
     * @return http state code
     */
    @PostMapping
    public ResponseEntity<String> createPost(
                @RequestBody PostUploadDto postUploadDto
            ){
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
}
