package com.keepgoingLikeline.emotionDiary_backend.config.oauth;

import java.io.IOException;
import java.time.Duration;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.keepgoingLikeline.emotionDiary_backend.config.jwt.TokenProvider;
import com.keepgoingLikeline.emotionDiary_backend.entity.RefreshToken;
import com.keepgoingLikeline.emotionDiary_backend.entity.UserEntity;
import com.keepgoingLikeline.emotionDiary_backend.repository.RefreshTokenRepository;
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
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserEntity user = userService.findByEmail(oAuth2User.getAttribute("email"));

        // 토큰 생성
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        
        // 리프레시 토큰을 데이터베이스에 저장하거나 업데이트
        refreshTokenService.createOrUpdateRefreshToken(user.getId(), refreshToken);

        // 쿠키에 리프레시 토큰 저장
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, (int) REFRESH_TOKEN_DURATION.getSeconds());

        // 쿠키에 액세스 토큰 저장
        CookieUtil.addCookie(response, "accessToken", accessToken, (int) ACCESS_TOKEN_DURATION.getSeconds());

        // 성공 후 리다이렉트될 URL 설정
        String targetUrl = determineTargetUrl(request, response, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return "/main";
    }
}
