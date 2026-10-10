package com.gallery.commn.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tools.jackson.databind.ObjectMapper;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    public LoginCheckInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginMember") == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            LoginErrorResponse errorResponse = new LoginErrorResponse(false, "로그인이 필요하거나 세션이 만료되었습니다.");

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private record LoginErrorResponse(boolean success, String message) {

    }

}
