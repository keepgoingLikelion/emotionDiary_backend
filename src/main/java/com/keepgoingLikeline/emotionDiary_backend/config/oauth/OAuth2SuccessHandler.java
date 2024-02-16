package com.keepgoingLikeline.emotionDiary_backend.config.oauth;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.keepgoingLikeline.emotionDiary_backend.config.jwt.TokenProvider;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.PostRepository;
import com.keepgoingLikeline.emotionDiary_backend.service.RefreshTokenService;
import com.keepgoingLikeline.emotionDiary_backend.service.UserService;
import com.keepgoingLikeline.emotionDiary_backend.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
	public static final String ACCESS_TOKEN_COKIE_NAME = "access_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Autowired
    PostRepository postRepository;
    
    @Value("${front.url}")
	private String frontUrl;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserEntity user = userService.findByEmail(oAuth2User.getAttribute("email"));

        // 토큰 생성
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        
        // refresh_token을 데이터베이스에 저장
        refreshTokenService.createOrUpdateRefreshToken(user.getId(), refreshToken);

        // cookie에 refresg_token 저장
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, (int) REFRESH_TOKEN_DURATION.getSeconds());
        // cookie에 access_token 저장
        CookieUtil.addCookie(response, ACCESS_TOKEN_COKIE_NAME, accessToken, (int) ACCESS_TOKEN_DURATION.getSeconds());

        String targetUrl = getRedirectUrl(user);
        response.setHeader("Location", targetUrl);
        response.setStatus(302);
    }
    
    private String getRedirectUrl(UserEntity user){
        if(user==null){
            return "/login";
        }
        if(postRepository.findByUserAndCreatedDate(user, LocalDate.now())==null){
            return frontUrl + "newPost";
        } else{
            return frontUrl + "main";
        }
    }
}
