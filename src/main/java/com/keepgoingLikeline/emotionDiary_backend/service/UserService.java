package com.keepgoingLikeline.emotionDiary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.keepgoingLikeline.emotionDiary_backend.dto.UserInfoResponse;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
	
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
	
	/**
     * authentication를 기반으로 userEntity를 받아오는 함수
     * 
     * @return userEntity
     */
    public UserEntity getUserEntity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            userEmail = (String) authentication.getPrincipal();
        }
        
        if(userEmail==null) return null;

        return userRepository.findByEmail(userEmail).orElse(null);
    }
    
    public boolean updateUserNickname(String newNickname) {
	    UserEntity currentUser = getUserEntity();
	    if (currentUser != null) {
	        currentUser.setNickname(newNickname);
	        userRepository.save(currentUser);
	        return true;
	    }
	    return false;
	}
    
    public UserInfoResponse getUserInfo() {
    	UserEntity userEntity = getUserEntity();
    	
    	if (userEntity == null) {
            return null;
        }
    	
    	UserInfoResponse response = new UserInfoResponse();
    	
    	response.setEmail(userEntity.getEmail());
    	response.setNickname(userEntity.getNickname());
    	
    	return response;
    }
}
