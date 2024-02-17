# Dockerfile
FROM amazoncorretto:17-alpine-jdk

# 애플리케이션 JAR 파일 경로와 Spring 프로파일, 추가 환경 변수를 위한 ARG 선언
ARG JAR_FILE=build/libs/*.jar
ARG PROFILES
ARG ENV

# Google OAuth 클라이언트 ID와 클라이언트 시크릿을 위한 ARG 선언
ARG SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID
ARG SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET

# JAR 파일을 Docker 이미지 내 app.jar로 복사
COPY ${JAR_FILE} app.jar

# Java 애플리케이션 실행. 여기서 Google OAuth 관련 시스템 프로퍼티를 전달
ENTRYPOINT ["java", \
            "-Dspring.profiles.active=${PROFILES}", \
            "-Dserver.env=${ENV}", \
            "-Dspring.security.oauth2.client.registration.google.client-id=${SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID}", \
            "-Dspring.security.oauth2.client.registration.google.client-secret=${SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET}", \
            "-jar", "app.jar"]
