package com.gallery.commn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gallery.commn.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;

    public WebConfig(LoginCheckInterceptor loginCheckInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
    }
    /**
     * 프론트와 백엔드 주소가 서로 다르면 withCredentials 설정
     * CORS 설정 시 credentials를 허용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"
                )
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                    "/api/member/login"
                    ,"/api/member/signup"
                    ,"/api/member/dplct"
                    ,"/api/member/me"
                    ,"/api/role/routes"
                    ,"/api/ntcn/unread/cnt"
                    ,"/api/mber/role/menu/all"
                    ,"/api/codes"
                );
        
    }
}
