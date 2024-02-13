package com.keepgoingLikeline.emotionDiary_backend.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.UserRepository;

@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		
		// 요청을 바탕으로 유저 정보를 담은 객체 반환
		OAuth2User user = super.loadUser(userRequest);
		saveOrUpdate(user);
		return user;
	}
	
	// 유저가 있으면 업테이트, 없으면 유저 생성
	private UserEntity saveOrUpdate(OAuth2User oAuth2User) {
	    Map<String, Object> attributes = oAuth2User.getAttributes();
	    String email = (String) attributes.get("email");
	    
	    UserEntity user = userRepository.findByEmail(email)
	            .map(entity -> {
	                return entity;
	            })
	            .orElse(UserEntity.builder()
	                    .email(email)
	                    .nickname((String) attributes.get("name"))
	                    .build());
	    
	    return userRepository.save(user);
	}
}
