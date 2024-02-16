package com.keepgoingLikeline.emotionDiary_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080", "https://keepgoinglikelion.github.io/meringue/")
            .allowCredentials(true)
            .exposedHeaders("authorization");
    }
}