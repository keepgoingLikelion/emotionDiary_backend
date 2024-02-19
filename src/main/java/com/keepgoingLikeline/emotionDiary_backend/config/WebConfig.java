package com.keepgoingLikeline.emotionDiary_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${front.url}")
	private String frontUrl;
	
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("http:/localhost:8080", frontUrl)
            .allowCredentials(true)
            .exposedHeaders("authorization");
    }
}