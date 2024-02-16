package com.keepgoingLikeline.emotionDiary_backend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.keepgoingLikeline.emotionDiary_backend.config.jwt.TokenProvider;
import com.keepgoingLikeline.emotionDiary_backend.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.keepgoingLikeline.emotionDiary_backend.config.oauth.OAuth2SuccessHandler;
import com.keepgoingLikeline.emotionDiary_backend.config.oauth.OAuth2UserCustomService;
import com.keepgoingLikeline.emotionDiary_backend.service.RefreshTokenService;
import com.keepgoingLikeline.emotionDiary_backend.service.UserService;

@Configuration
public class WebOAuthSecurityConfig {
	
	@Autowired
	private OAuth2UserCustomService oAuth2UserCustomService;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private UserService userService;
	
	@Value("${front.url}")
	private String frontUrl;
	
	@Bean
	public WebSecurityCustomizer configure() { // 스프링 시큐리티 기능 비활성화
		return (web) -> web.ignoring() // ignoring(무시하다)
				.requestMatchers(PathRequest.toH2Console());
				//.requestMatchers("/static/**"); -> rest api개발에는 정정파일 필요x
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// 토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼로그인, 세션 비활성화
		http.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.logout().disable();
		
		http.cors();
		
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// 헤터를 확인ㅇ할 커스텀 필터 추가
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정. 나머지 API URL은 인증 필요
		http.authorizeRequests()
				.requestMatchers("/api/token").permitAll()
				.requestMatchers("/api/**").authenticated()
				.anyRequest().permitAll();
		
		http.oauth2Login()	
				.loginPage("/login")
				.authorizationEndpoint()
				// Authorization 요청과 관련된 상태 저장
				.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
				.and()
				.successHandler(oAuth2SuccessHandler())	// 인증 성공 시 실행할 핸들러
				.userInfoEndpoint()
				.userService(oAuth2UserCustomService);
		
		http.logout()
				.logoutSuccessUrl("/login");
		
		// /api로 시작하는 url인 경우 401 상태 코드를 반환하도록 예외 처리
		http.exceptionHandling()
				.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), 
						new AntPathRequestMatcher("/api/**"));
		
		return http.build();
	}
	
	@Bean
	public OAuth2SuccessHandler oAuth2SuccessHandler() {
		return new OAuth2SuccessHandler(tokenProvider, refreshTokenService, userService);
	}
	
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider);
	}
	
	@Bean
	public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontUrl)); // 허용할 오리진 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 허용할 HTTP 메소드 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
