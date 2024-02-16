package com.keepgoingLikeline.emotionDiary_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${front.url}")
	private String frontUrl;
	@Value("${server.address}")
	private String serverAddress;
	
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("http://" + ("localhost".equals(serverAddress) ? "localhost:8080" : serverAddress), frontUrl)
            .allowCredentials(true)
            .exposedHeaders("authorization");
    }
}