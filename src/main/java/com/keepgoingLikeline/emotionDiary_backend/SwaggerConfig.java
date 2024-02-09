package com.keepgoingLikeline.emotionDiary_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * https://hogwart-scholars.tistory.com/entry/Spring-Boot-SpringDoc%EA%B3%BC-Swagger%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-API-%EB%AC%B8%EC%84%9C%ED%99%94-%EC%9E%90%EB%8F%99%ED%99%94%ED%95%98%EA%B8%B0
 */
@OpenAPIDefinition(
   info = @Info(
      title = "머랭 프로젝트 API 명세서",
      description = "머랭 프로젝트에 사용되는 API 명세서",
      version = "v1"
   )
)
@Configuration
public class SwaggerConfig {
 
   private static final String BEARER_TOKEN_PREFIX = "Bearer";
 
   @Bean
   // 운영 환경에는 Swagger를 비활성화하기 위해 추가했습니다.
   @Profile("!Prod")
   public OpenAPI openAPI() {
      String jwtSchemeName = "basicAuth";
      SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
      Components components = new Components()
         .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
            .name(jwtSchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme(BEARER_TOKEN_PREFIX)
            .bearerFormat("JWT"));
 
      // Swagger UI 접속 후, 딱 한 번만 accessToken을 입력해주면 모든 API에 토큰 인증 작업이 적용됩니다.
      return new OpenAPI()
         .addSecurityItem(securityRequirement)
         .components(components);
   }
 
}