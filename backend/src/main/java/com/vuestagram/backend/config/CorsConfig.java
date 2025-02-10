package com.vuestagram.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")  // 모든 요청에 대해 CORS 허용
	                .allowedOrigins("http://localhost:8080") // 허용할 Origin 설정 (프론트엔드 URL)
	                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드 설정
	                .allowedHeaders("*")  // 허용할 헤더 설정
	                .allowCredentials(true);  // 인증 정보를 허용
	    }
}
